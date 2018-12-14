package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "preludes")
public class Prelude {

    public static final String PRELUDE_FIELD_ID = "_id";
    public static final String PRELUDE_FIELD_NAME_EN = "name_en";
    public static final String PRELUDE_FIELD_NAME_RU = "name_ru";
    public static final String PRELUDE_FIELD_EXPANSION_ID = "expansion_id";
    public static final String PRELUDE_FIELD_TEXT_EN = "text_en";
    public static final String PRELUDE_FIELD_TEXT_RU = "text_ru";

    @PrimaryKey
    @ColumnInfo(name = PRELUDE_FIELD_ID)
    private int id;

    @ColumnInfo(name = PRELUDE_FIELD_NAME_EN)
    private String nameEN;

    @ColumnInfo(name = PRELUDE_FIELD_NAME_RU)
    private String nameRU;

    @ColumnInfo(name = PRELUDE_FIELD_EXPANSION_ID)
    private int expansionID;

    @ColumnInfo(name = PRELUDE_FIELD_TEXT_EN)
    private String textEN;

    @ColumnInfo(name = PRELUDE_FIELD_TEXT_RU)
    private String textRU;

    public Prelude() {
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

    public String getTextEN() {
        return textEN;
    }

    public void setTextEN(String textEN) {
        this.textEN = textEN;
    }

    public String getTextRU() {
        return textRU;
    }

    public void setTextRU(String textRU) {
        this.textRU = textRU;
    }

    public String getName() {
        if (Localization.getInstance().isRusLocale()) return nameRU;
        else return nameEN;
    }

    public String getText() {
        if (Localization.getInstance().isRusLocale()) return textRU;
        else return textEN;
    }
}