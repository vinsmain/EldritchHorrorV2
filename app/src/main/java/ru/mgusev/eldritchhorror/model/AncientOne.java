package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ancient_ones")
public class AncientOne {

    public static final String ANCIENT_ONE_FIELD_ID = "_id";
    public static final String ANCIENT_ONE_FIELD_IMAGE_RESOURCE = "image_resource";
    public static final String ANCIENT_ONE_FIELD_NAME_EN = "name_en";
    public static final String ANCIENT_ONE_FIELD_NAME_RU = "name_ru";
    public static final String ANCIENT_ONE_EXPANSION_ID = "expansion_id";
    public static final String ANCIENT_ONE_MAX_MYSTERIES = "max_mysteries";

    @PrimaryKey
    @ColumnInfo(name = ANCIENT_ONE_FIELD_ID)
    private int id;

    @ColumnInfo(name = ANCIENT_ONE_FIELD_IMAGE_RESOURCE)
    private String imageResource;

    @ColumnInfo(name = ANCIENT_ONE_FIELD_NAME_EN)
    private String nameEN;

    @ColumnInfo(name = ANCIENT_ONE_FIELD_NAME_RU)
    private String nameRU;

    @ColumnInfo(name = ANCIENT_ONE_EXPANSION_ID)
    private int expansionID;

    @ColumnInfo(name = ANCIENT_ONE_MAX_MYSTERIES)
    private int maxMysteries;

    public AncientOne() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameRU() {
        return nameRU;
    }

    public void setNameRU(String nameRU) {
        this.nameRU = nameRU;
    }

    public int getExpansionID() {
        return expansionID;
    }

    public void setExpansionID(int expansionID) {
        this.expansionID = expansionID;
    }

    public int getMaxMysteries() {
        return maxMysteries;
    }

    public void setMaxMysteries(int maxMysteries) {
        this.maxMysteries = maxMysteries;
    }

    public String getName() {
        if (Localization.getInstance().isRusLocale()) return nameRU;
        else return nameEN;
    }
}