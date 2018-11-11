package ru.mgusev.eldritchhorror.repository;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.facebook.common.util.UriUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if (uriFileList.size() == 0) Log.d("DELETE FILE DIR", Objects.requireNonNull(dir).getPath() + " " + dir.delete());
        return uriFileList;
    }

    public File getImageFile(String imageFileName, long gameId) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + gameId);
        return new File(storageDir, imageFileName);
    }

    public void copyFile(File source, File dest) throws IOException {
        FileChannel sourceChannel = new FileInputStream(source).getChannel();
        FileChannel destChannel = new FileOutputStream(dest).getChannel();
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        sourceChannel.close();
        destChannel.close();
    }

    public void deleteRecursiveFiles(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) deleteRecursiveFiles(child);
        }
        Log.d("DELETE FILE", fileOrDirectory.getPath() + " " + fileOrDirectory.delete());
        if (!fileOrDirectory.isDirectory() && fileOrDirectory.getParentFile().listFiles().length == 0) {
            Log.d("DELETE FILE", fileOrDirectory.getPath() + " " + fileOrDirectory.getParentFile().delete());
        }
    }
}
