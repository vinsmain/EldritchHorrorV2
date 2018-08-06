package ru.mgusev.eldritchhorror.androidmaterialgallery;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amardeep on 10/25/2017.
 */

public class GalleryUtils {

    //Define bucket name from which you want to take images Example '/DCIM/Camera' for camera images
    public static String CAMERA_IMAGE_BUCKET_NAME;// = Environment.getDataDirectory().toString();

    static ArrayList<GalleryItem> f = new ArrayList<>();// list of file paths
    public static File[] listFile;

    //method to get id of image bucket from path
    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    //method to get images
    public static List<GalleryItem> getImages(Context context) {
        /*final String[] projection = {MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA};
        final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        CAMERA_IMAGE_BUCKET_NAME = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        final String[] selectionArgs = {GalleryUtils.getBucketId(CAMERA_IMAGE_BUCKET_NAME)};
        System.out.println("PATH " + CAMERA_IMAGE_BUCKET_NAME);
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        ArrayList<GalleryItem> result = new ArrayList<GalleryItem>(cursor.getCount());
        System.out.println("RESULT " + result.size());
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            final int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            do {
                GalleryItem galleryItem = new GalleryItem(cursor.getString(dataColumn), cursor.getString(nameColumn));
                result.add(galleryItem);
            } while (cursor.moveToNext());
        }
        cursor.close();*/

        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (file.isDirectory())
        {
            listFile = file.listFiles();


            for (int i = 0; i < listFile.length; i++)
            {

                f.add(new GalleryItem(listFile[i].getAbsolutePath(), listFile[i].getName()));

            }
        }


        return f;

    }
}
