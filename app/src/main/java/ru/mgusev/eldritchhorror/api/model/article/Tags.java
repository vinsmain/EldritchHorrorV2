package ru.mgusev.eldritchhorror.api.model.article;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags {

    @SerializedName("typeAlias")
    @Expose
    private Object typeAlias;
    @SerializedName("itemTags")
    @Expose
    private List<Object> itemTags = null;

    public Object getTypeAlias() {
        return typeAlias;
    }

    public void setTypeAlias(Object typeAlias) {
        this.typeAlias = typeAlias;
    }

    public List<Object> getItemTags() {
        return itemTags;
    }

    public void setItemTags(List<Object> itemTags) {
        this.itemTags = itemTags;
    }
}