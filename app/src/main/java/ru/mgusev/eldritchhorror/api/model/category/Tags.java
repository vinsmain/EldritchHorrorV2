package ru.mgusev.eldritchhorror.api.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags {

    @SerializedName("typeAlias")
    @Expose
    private Object typeAlias;
    @SerializedName("tags")
    @Expose
    private String tags;

    public Object getTypeAlias() {
        return typeAlias;
    }

    public void setTypeAlias(Object typeAlias) {
        this.typeAlias = typeAlias;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}