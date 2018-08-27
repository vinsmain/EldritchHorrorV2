package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "files")
public class ImageFile {

    public static final String IMAGE_FILE_FIELD_ID = "_id";
    public static final String IMAGE_FILE_FIELD_GAME_ID = "game_id";
    public static final String IMAGE_FILE_FIELD_NAME = "name";
    public static final String IMAGE_FILE_FIELD_COMMENT = "comment";
    public static final String IMAGE_FILE_FIELD_USER_ID = "user_id";
    public static final String IMAGE_FILE_FIELD_LAST_MODIFIED = "last_modified";
    public static final String IMAGE_FILE_FIELD_MG5_HASH = "md5_hash";

    @PrimaryKey
    @ColumnInfo(name = IMAGE_FILE_FIELD_ID)
    private long id;

    @ColumnInfo(name = IMAGE_FILE_FIELD_GAME_ID)
    private long gameId;

    @ColumnInfo(name = IMAGE_FILE_FIELD_NAME)
    private String name;

    @ColumnInfo(name = IMAGE_FILE_FIELD_COMMENT)
    private String comment;

    @ColumnInfo(name = IMAGE_FILE_FIELD_USER_ID)
    private String userID;

    @ColumnInfo(name = IMAGE_FILE_FIELD_LAST_MODIFIED)
    private long lastModified;

    @ColumnInfo(name = IMAGE_FILE_FIELD_MG5_HASH)
    private String md5Hash;

    public ImageFile() {
        this.id = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getMd5Hash() {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash) {
        this.md5Hash = md5Hash;
    }
}