package ru.mgusev.eldritchhorror.api.model.article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private ArticleData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArticleData getData() {
        return data;
    }

    public void setData(ArticleData data) {
        this.data = data;
    }
}