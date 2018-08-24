package ru.mgusev.eldritchhorror.model;

import java.util.ArrayList;
import java.util.List;

public class ImageFileList {
    private List<ImageFile> fileList;

    public ImageFileList() {
        this.fileList = new ArrayList<>();
    }

    public List<ImageFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ImageFile> fileList) {
        this.fileList = fileList;
    }
}
