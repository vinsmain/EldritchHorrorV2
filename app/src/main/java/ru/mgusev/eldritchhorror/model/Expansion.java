package ru.mgusev.eldritchhorror.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "expansions")
public class Expansion {

    public static final String EXPANSION_FIELD_ID = "_id";
    public static final String EXPANSION_FIELD_IMAGE_RESOURCE = "image_resource";
    public static final String EXPANSION_FIELD_NAME_EN = "name_en";
    public static final String EXPANSION_FIELD_NAME_RU = "name_ru";
    public static final String EXPANSION_FIELD_IS_ENABLE = "is_enable";

    @DatabaseField(dataType = DataType.INTEGER, generatedId = true, columnName = EXPANSION_FIELD_ID)
    private int id;

    @DatabaseField(dataType = DataType.STRING, columnName = EXPANSION_FIELD_IMAGE_RESOURCE)
    private String imageResource;

    @DatabaseField(dataType = DataType.STRING, columnName = EXPANSION_FIELD_NAME_EN)
    private String nameEN;

    @DatabaseField(dataType = DataType.STRING, columnName = EXPANSION_FIELD_NAME_RU)
    private String nameRU;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = EXPANSION_FIELD_IS_ENABLE)
    private boolean isEnable;

    public Expansion() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        if (Localization.getInstance().isRusLocale()) return nameRU;
        else return nameEN;
    }

    public String getImageResource() {
        return imageResource;
    }

    public boolean isEnable() {
        return isEnable;
    }
}
