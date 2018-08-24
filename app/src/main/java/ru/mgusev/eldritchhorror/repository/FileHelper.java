package ru.mgusev.eldritchhorror.repository;

import android.content.Context;
import android.os.Environment;

import com.facebook.common.util.UriUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileHelper {

    private Context context;
    private List<String> uriFileList;

    public FileHelper(Context context) {
        this.context = context;
    }

    public List<String> getImages(long gameId) {
        uriFileList = new ArrayList<>();
        File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + gameId);

        if (dir != null && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                uriFileList.add(UriUtil.getUriForFile(file).toString());
            }
        }
        return uriFileList;
    }

    public File createImageFile(String imageFileName, long gameId) throws IOException {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + gameId);
        return File.createTempFile(
                imageFileName,   /* prefix */
                ".jpg",    /* suffix */
                storageDir       /* directory */
        ); //TODO Разобраться с временными файлами
    }

    public void deleteRecursiveFiles(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursiveFiles(child);

        fileOrDirectory.delete();

        //TODO Предусмотреть удаление пустых папок
    }
}
