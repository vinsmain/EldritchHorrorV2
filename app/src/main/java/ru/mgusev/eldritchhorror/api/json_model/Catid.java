package ru.mgusev.eldritchhorror.api.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Catid {

    @SerializedName("catid")
    @Expose
    private String catid;
    @SerializedName("title")
    @Expose
    private String title;

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}