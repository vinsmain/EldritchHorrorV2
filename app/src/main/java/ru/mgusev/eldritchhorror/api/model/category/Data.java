package ru.mgusev.eldritchhorror.api.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("lft")
    @Expose
    private String lft;
    @SerializedName("rgt")
    @Expose
    private String rgt;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("asset_id")
    @Expose
    private String assetId;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("checked_out")
    @Expose
    private String checkedOut;
    @SerializedName("checked_out_time")
    @Expose
    private String checkedOutTime;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("params")
    @Expose
    private Params params;
    @SerializedName("metadesc")
    @Expose
    private String metadesc;
    @SerializedName("metakey")
    @Expose
    private String metakey;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("created_user_id")
    @Expose
    private String createdUserId;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("modified_user_id")
    @Expose
    private String modifiedUserId;
    @SerializedName("modified_time")
    @Expose
    private Object modifiedTime;
    @SerializedName("hits")
    @Expose
    private String hits;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("tags")
    @Expose
    private Tags tags;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLft() {
        return lft;
    }

    public void setLft(String lft) {
        this.lft = lft;
    }

    public String getRgt() {
        return rgt;
    }

    public void setRgt(String rgt) {
        this.rgt = rgt;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public String getMetadesc() {
        return metadesc;
    }

    public void setMetadesc(String metadesc) {
        this.metadesc = metadesc;
    }

    public String getMetakey() {
        return metakey;
    }

    public void setMetakey(String metakey) {
        this.metakey = metakey;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public Object getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Object modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}