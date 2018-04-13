package ru.mgusev.eldritchhorror.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ancient_ones")
public class AncientOne {

    public static final String ANCIENT_ONE_FIELD_ID = "_id";
    public static final String ANCIENT_ONE_FIELD_IMAGE_RESOURCE = "image_resource";
    public static final String ANCIENT_ONE_FIELD_NAME_EN = "name_en";
    public static final String ANCIENT_ONE_FIELD_NAME_RU = "name_ru";
    public static final String ANCIENT_ONE_EXPANSION_ID = "expansion_id";
    public static final String ANCIENT_ONE_MAX_MYSTERIES = "max_mysteries";

    @DatabaseField(dataType = DataType.INTEGER, generatedId = true, columnName = ANCIENT_ONE_FIELD_ID)
    private int id;

    @DatabaseField(dataType = DataType.STRING, columnName = ANCIENT_ONE_FIELD_IMAGE_RESOURCE)
    private String imageResource;

    @DatabaseField(dataType = DataType.STRING, columnName = ANCIENT_ONE_FIELD_NAME_EN)
    private String nameEN;

    @DatabaseField(dataType = DataType.STRING, columnName = ANCIENT_ONE_FIELD_NAME_RU)
    private String nameRU;

    @DatabaseField(dataType = DataType.INTEGER, columnName = ANCIENT_ONE_EXPANSION_ID)
    private int expansionID;

    @DatabaseField(dataType = DataType.INTEGER, columnName = ANCIENT_ONE_MAX_MYSTERIES)
    private int maxMysteries;

    public AncientOne() {
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

    public int getExpansionID() {
        return expansionID;
    }

    public int getMaxMysteries() {
        return maxMysteries;
    }
}
