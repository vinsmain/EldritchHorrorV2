package ru.mgusev.eldritchhorror.androidmaterialgallery;

/**
 * Created by amardeep on 11/3/2017.
 */
//This class represents single gallery item
public class GalleryItem {
    public String imageUri;
    public String imageName;
    public boolean isSelected = false;

    public GalleryItem(String imageUri, String imageName) {
        this.imageUri = imageUri;
        this.imageName = imageName;
    }
}
