package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "rumors")
public class Rumor {

    public static final String RUMOR_FIELD_ID = "_id";
    public static final String RUMOR_FIELD_NAME_EN = "name_en";
    public static final String RUMOR_FIELD_NAME_RU = "name_ru";
    public static final String RUMOR_FIELD_EXPANSION_ID = "expansion_id";

    @PrimaryKey
    @ColumnInfo(name = RUMOR_FIELD_ID)
    private int id;

    @ColumnInfo(name = RUMOR_FIELD_NAME_EN)
    private String nameEN;

    @ColumnInfo(name = RUMOR_FIELD_NAME_RU)
    private String nameRU;

    @ColumnInfo(name = RUMOR_FIELD_EXPANSION_ID)
    private int expansionID;

    public Rumor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        if (Localization.getInstance().isRusLocale()) return nameRU;
        else return nameEN;
    }
}