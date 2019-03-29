package ru.mgusev.eldritchhorror.api.json_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

    @SerializedName("tag_id")
    @Expose
    private int tagId;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("lft")
    @Expose
    private int lft;
    @SerializedName("rgt")
    @Expose
    private int rgt;
    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("published")
    @Expose
    private int published;
    @SerializedName("checked_out")
    @Expose
    private String checkedOut;
    @SerializedName("checked_out_time")
    @Expose
    private String checkedOutTime;
    @SerializedName("access")
    @Expose
    private int access;
    @SerializedName("params")
    @Expose
    private String params;
    @SerializedName("created_user_id")
    @Expose
    private int createdUserId;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("created_by_alias")
    @Expose
    private String createdByAlias;
    @SerializedName("modified_user_id")
    @Expose
    private int modifiedUserId;
    @SerializedName("modified_time")
    @Expose
    private String modifiedTime;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("urls")
    @Expose
    private String urls;
    @SerializedName("hits")
    @Expose
    private int hits;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("publish_up")
    @Expose
    private String publishUp;
    @SerializedName("publish_down")
    @Expose
    private String publishDown;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLft() {
        return lft;
    }

    public void setLft(int lft) {
        this.lft = lft;
    }

    public int getRgt() {
        return rgt;
    }

    public void setRgt(int rgt) {
        this.rgt = rgt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(String checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getCheckedOutTime() {
        return checkedOutTime;
    }

    public void setCheckedOutTime(String checkedOutTime) {
        this.checkedOutTime = checkedOutTime;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(int createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedByAlias() {
        return createdByAlias;
    }

    public void setCreatedByAlias(String createdByAlias) {
        this.createdByAlias = createdByAlias;
    }

    public int getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(int modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
}
