package ru.mgusev.eldritchhorror.api.json_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags {

    @SerializedName("typeAlias")
    @Expose
    private Object typeAlias;
    @SerializedName("itemTags")
    @Expose
    private List<Tag> itemTags = null;

    public Object getTypeAlias() {
        return typeAlias;
    }

    public void setTypeAlias(Object typeAlias) {
        this.typeAlias = typeAlias;
    }

    public List<Tag> getItemTags() {
        return itemTags;
    }

    public void setItemTags(List<Tag> itemTags) {
        this.itemTags = itemTags;
    }
}