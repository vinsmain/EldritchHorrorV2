package ru.mgusev.eldritchhorror.api.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private ArticleData articleData;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArticleData getArticleData() {
        return articleData;
    }

    public void setArticleData(ArticleData articleData) {
        this.articleData = articleData;
    }
}