package ru.mgusev.eldritchhorror.api.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("introtext")
    @Expose
    private String introtext;
    @SerializedName("fulltext")
    @Expose
    private String fulltext;
    @SerializedName("catid")
    @Expose
    private Catid catid;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("publish_up")
    @Expose
    private String publishUp;
    @SerializedName("publish_down")
    @Expose
    private String publishDown;
    @SerializedName("images")
    @Expose
    private Images images;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("featured")
    @Expose
    private String featured;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("hits")
    @Expose
    private String hits;
    @SerializedName("created_by")
    @Expose
    private CreatedBy createdBy;
    @SerializedName("tags")
    @Expose
    private Tags tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIntrotext() {
        return introtext;
    }

    public void setIntrotext(String introtext) {
        this.introtext = introtext;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public Catid getCatid() {
        return catid;
    }

    public void setCatid(Catid catid) {
        this.catid = catid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getPublishUp() {
        return publishUp;
    }

    public void setPublishUp(String publishUp) {
        this.publishUp = publishUp;
    }

    public String getPublishDown() {
        return publishDown;
    }

    public void setPublishDown(String publishDown) {
        this.publishDown = publishDown;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}