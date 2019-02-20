package ru.mgusev.eldritchhorror.api.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Params {

    @SerializedName("category_layout")
    @Expose
    private String categoryLayout;
    @SerializedName("image")
    @Expose
    private String image;

    public String getCategoryLayout() {
        return categoryLayout;
    }

    public void setCategoryLayout(String categoryLayout) {
        this.categoryLayout = categoryLayout;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}