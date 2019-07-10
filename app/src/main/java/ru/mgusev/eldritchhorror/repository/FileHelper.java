package ru.mgusev.eldritchhorror.repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.facebook.common.util.UriUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class FileHelper {

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
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + gameId);

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
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + gameId);
        return new File(storageDir, imageFileName);
    }

    public void copyFile(File source, File dest) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(Objects.requireNonNull(getImageContentUri(context, source)));
        FileOutputStream fileOutputStream = new FileOutputStream(dest);
        copyStream(Objects.requireNonNull(inputStream), fileOutputStream);
        fileOutputStream.close();
        inputStream.close();
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
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
