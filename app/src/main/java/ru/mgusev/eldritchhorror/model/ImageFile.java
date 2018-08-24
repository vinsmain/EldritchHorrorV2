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

    @PrimaryKey
    @ColumnInfo(name = IMAGE_FILE_FIELD_ID)
    private long id;

    @ColumnInfo(name = IMAGE_FILE_FIELD_GAME_ID)
    private long gameId;

    @ColumnInfo(name = IMAGE_FILE_FIELD_NAME)
    private String name;

    @ColumnInfo(name = IMAGE_FILE_FIELD_COMMENT)
    private String comment;

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
}