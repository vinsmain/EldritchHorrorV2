package ru.mgusev.eldritchhorror.api.model.article;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleData {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}