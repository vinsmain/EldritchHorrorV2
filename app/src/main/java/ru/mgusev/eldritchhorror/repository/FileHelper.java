package ru.mgusev.eldritchhorror.repository;

import android.content.ContentValues;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.media.MediaScannerConnection;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import com.facebook.common.util.UriUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class FileHelper {

    public static final int GALLERY_EXPORT_SAVED = 1;
    public static final int GALLERY_EXPORT_ALREADY_EXISTS = 2;
    public static final int GALLERY_EXPORT_MISSING = 3;
    public static final int GALLERY_EXPORT_FAILED = 4;

    private static final String GALLERY_DIRECTORY = "Pictures/Eldritch Horror";

    private Context context;
    private List<String> uriFileList;

    public FileHelper(Context context) {
        this.context = context;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ", new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    public List<String> getImages(long gameId) {
        uriFileList = new ArrayList<>();
        File dir = getImageDirectory(gameId);

        if (dir != null && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                uriFileList.add(UriUtil.getUriForFile(file).toString());
            }
        }
        if (uriFileList.size() == 0 && dir != null)
            Timber.tag("DELETE FILE DIR").d(Objects.requireNonNull(dir).getPath() + " " + dir.delete());
        return uriFileList;
    }

    public File getImageFile(String imageFileName, long gameId) {
        if (imageFileName == null || imageFileName.trim().isEmpty()) {
            Timber.e("Не удалось получить файл фотографии: имя отсутствует");
            return null;
        }

        File storageDir = getImageDirectory(gameId);
        if (storageDir == null) {
            Timber.e("Хранилище для фотографий недоступно");
            return null;
        }
        if (!storageDir.exists() && !storageDir.mkdirs()) {
            Timber.e("Не удалось создать каталог для фотографий: %s", storageDir);
            return null;
        }
        return new File(storageDir, imageFileName);
    }

    private File getImageDirectory(long gameId) {
        File picturesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return picturesDirectory == null ? null : new File(picturesDirectory, Long.toString(gameId));
    }

    public List<File> getLocalImageFiles(long gameId) {
        List<File> imageFiles = new ArrayList<>();
        File directory = getImageDirectory(gameId);
        if (directory == null || !directory.isDirectory()) return imageFiles;

        File[] files = directory.listFiles();
        if (files == null) return imageFiles;
        for (File file : files) {
            if (file.isFile()) imageFiles.add(file);
        }
        return imageFiles;
    }

    public int saveImageToGallery(File source, String displayName) {
        if (source == null || !source.isFile() || source.length() == 0) return GALLERY_EXPORT_MISSING;
        if (displayName == null || displayName.trim().isEmpty()) return GALLERY_EXPORT_FAILED;

        try {
            ContentResolver resolver = context.getContentResolver();
            Uri existingImage = findGalleryImage(resolver, displayName);
            if (existingImage != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && isPending(resolver, existingImage)) {
                    resolver.delete(existingImage, null, null);
                } else {
                    return GALLERY_EXPORT_ALREADY_EXISTS;
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                return saveImageToScopedGallery(resolver, source, displayName);
            }
            return saveImageToLegacyGallery(source, displayName);
        } catch (RuntimeException exception) {
            Timber.tag("GALLERY_EXPORT").e(exception, "Не удалось сохранить фотографию в галерею");
            return GALLERY_EXPORT_FAILED;
        }
    }

    private int saveImageToScopedGallery(ContentResolver resolver, File source, String displayName) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
        values.put(MediaStore.Images.Media.MIME_TYPE, getMimeType(displayName));
        values.put(MediaStore.Images.Media.RELATIVE_PATH, GALLERY_DIRECTORY);
        values.put(MediaStore.Images.Media.IS_PENDING, 1);

        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (imageUri == null) return GALLERY_EXPORT_FAILED;

        try {
            copyLocalFile(source, resolver.openOutputStream(imageUri, "w"));
            ContentValues publishedValues = new ContentValues();
            publishedValues.put(MediaStore.Images.Media.IS_PENDING, 0);
            resolver.update(imageUri, publishedValues, null, null);
            return GALLERY_EXPORT_SAVED;
        } catch (IOException | RuntimeException exception) {
            resolver.delete(imageUri, null, null);
            Timber.tag("GALLERY_EXPORT").e(exception, "Не удалось сохранить фотографию в галерею");
            return GALLERY_EXPORT_FAILED;
        }
    }

    private int saveImageToLegacyGallery(File source, String displayName) {
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File galleryDirectory = new File(picturesDirectory, "Eldritch Horror");
        if (!galleryDirectory.isDirectory() && !galleryDirectory.mkdirs()) return GALLERY_EXPORT_FAILED;

        File destination = new File(galleryDirectory, displayName);
        if (destination.exists()) {
            MediaScannerConnection.scanFile(context, new String[]{destination.getAbsolutePath()},
                    new String[]{getMimeType(displayName)}, null);
            return GALLERY_EXPORT_ALREADY_EXISTS;
        }
        try {
            try (InputStream input = new FileInputStream(source);
                 OutputStream output = new FileOutputStream(destination)) {
                copyStream(input, output);
            }
            MediaScannerConnection.scanFile(context, new String[]{destination.getAbsolutePath()},
                    new String[]{getMimeType(displayName)}, null);
            return GALLERY_EXPORT_SAVED;
        } catch (IOException | RuntimeException exception) {
            if (destination.exists()) destination.delete();
            Timber.tag("GALLERY_EXPORT").e(exception, "Не удалось сохранить фотографию в галерею");
            return GALLERY_EXPORT_FAILED;
        }
    }

    private Uri findGalleryImage(ContentResolver resolver, String displayName) {
        String selection;
        String[] selectionArgs;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            selection = MediaStore.Images.Media.DISPLAY_NAME + "=? AND "
                    + MediaStore.Images.Media.RELATIVE_PATH + "=?";
            selectionArgs = new String[]{displayName, GALLERY_DIRECTORY + "/"};
        } else {
            File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            selection = MediaStore.Images.Media.DATA + "=?";
            selectionArgs = new String[]{new File(new File(picturesDirectory, "Eldritch Horror"), displayName).getAbsolutePath()};
        }

        try (Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.toString(id));
            }
        } catch (RuntimeException exception) {
            Timber.tag("GALLERY_EXPORT").w(exception, "Не удалось проверить фотографию в галерее");
        }
        return null;
    }

    private boolean isPending(ContentResolver resolver, Uri imageUri) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return false;
        try (Cursor cursor = resolver.query(imageUri,
                new String[]{MediaStore.Images.Media.IS_PENDING}, null, null, null)) {
            return cursor != null && cursor.moveToFirst()
                    && cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.IS_PENDING)) != 0;
        } catch (RuntimeException exception) {
            Timber.tag("GALLERY_EXPORT").w(exception, "Не удалось проверить состояние фотографии в галерее");
            return false;
        }
    }

    private String getMimeType(String fileName) {
        int extensionSeparator = fileName.lastIndexOf('.');
        String extension = extensionSeparator < 0 ? "" : fileName.substring(extensionSeparator + 1);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase(java.util.Locale.ROOT));
        return mimeType == null ? "image/jpeg" : mimeType;
    }

    public String getImageExtension(File imageFile) {
        String mimeType = getImageMimeType(imageFile);
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        if (extension != null) return "." + extension;

        String fileName = imageFile.getName();
        int extensionSeparator = fileName.lastIndexOf('.');
        return extensionSeparator < 0 ? ".jpg" : fileName.substring(extensionSeparator).toLowerCase(java.util.Locale.ROOT);
    }

    private String getImageMimeType(File imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        return options.outMimeType == null ? getMimeType(imageFile.getName()) : options.outMimeType;
    }

    private static void copyLocalFile(File source, OutputStream output) throws IOException {
        if (output == null) throw new IOException("Не удалось открыть файл галереи для записи");
        try (InputStream input = new FileInputStream(source); OutputStream outputStream = output) {
            copyStream(input, outputStream);
        }
    }

    public void copyFile(File source, File dest) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(Objects.requireNonNull(getImageContentUri(context, source)));
        FileOutputStream fileOutputStream = new FileOutputStream(dest);
        copyStream(Objects.requireNonNull(inputStream), fileOutputStream);
        fileOutputStream.close();
        inputStream.close();
    }

    public void copyUriToFile(Uri source, File dest) throws IOException {
        try (InputStream inputStream = context.getContentResolver().openInputStream(source);
             FileOutputStream outputStream = new FileOutputStream(dest)) {
            copyStream(Objects.requireNonNull(inputStream), outputStream);
        }
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[64 * 1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public void deleteRecursiveFiles(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) deleteRecursiveFiles(child);
        }
        Timber.tag("DELETE FILE").d(fileOrDirectory.getPath() + " " + fileOrDirectory.delete());
        if (!fileOrDirectory.isDirectory() && fileOrDirectory.getParentFile().listFiles().length == 0) {
            Timber.tag("DELETE FILE").d(fileOrDirectory.getPath() + " " + fileOrDirectory.getParentFile().delete());
        }
    }

    public void shareImageIntent(List<Uri> imageUriList) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, (ArrayList<? extends Parcelable>) imageUriList);
        sendIntent.setType("image/*");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }
}
