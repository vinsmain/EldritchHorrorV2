package ru.mgusev.eldritchhorror.database.oldDB;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ru.mgusev.eldritchhorror.model.Localization;

@DatabaseTable(tableName = "investigators")
public class InvestigatorOld {

    public static final String INVESTIGATOR_TABLE_NAME = "investigators";

    public static final String INVESTIGATOR_FIELD_ID = "_id";
    public static final String INVESTIGATOR_FIELD_GAME_ID = "game_id";
    public static final String INVESTIGATOR_FIELD_IMAGE_RESOURCE = "image_resource";
    public static final String INVESTIGATOR_FIELD_IS_MALE = "is_male";
    public static final String INVESTIGATOR_FIELD_NAME_EN = "name_en";
    public static final String INVESTIGATOR_FIELD_NAME_RU = "name_ru";
    public static final String INVESTIGATOR_FIELD_OCCUPATION_EN = "occupation_en";
    public static final String INVESTIGATOR_FIELD_OCCUPATION_RU = "occupation_ru";
    public static final String INVESTIGATOR_FIELD_IS_STARTING = "is_starting";
    public static final String INVESTIGATOR_FIELD_IS_REPLACEMENT = "is_replacement";
    public static final String INVESTIGATOR_FIELD_IS_DEAD = "is_dead";
    public static final String INVESTIGATOR_FIELD_EXPANSION_ID = "expansion_id";
    public static final String INVESTIGATOR_FIELD_SPECIALIZATION_ID = "specialization_id";

    @DatabaseField(id = true, dataType = DataType.LONG, columnName = INVESTIGATOR_FIELD_ID)
    public long id;

    @DatabaseField(dataType = DataType.LONG, columnName = INVESTIGATOR_FIELD_GAME_ID)
    public long gameId;

    @DatabaseField(dataType = DataType.STRING, columnName = INVESTIGATOR_FIELD_IMAGE_RESOURCE)
    public String imageResource;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = INVESTIGATOR_FIELD_IS_MALE)
    public boolean isMale;

    @DatabaseField(dataType = DataType.STRING, columnName = INVESTIGATOR_FIELD_NAME_EN)
    public String nameEN;

    @DatabaseField(dataType = DataType.STRING, columnName = INVESTIGATOR_FIELD_NAME_RU)
    public String nameRU;

    @DatabaseField(dataType = DataType.STRING, columnName = INVESTIGATOR_FIELD_OCCUPATION_EN)
    public String occupationEN;

    @DatabaseField(dataType = DataType.STRING, columnName = INVESTIGATOR_FIELD_OCCUPATION_RU)
    public String occupationRU;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = INVESTIGATOR_FIELD_IS_STARTING)
    public boolean isStarting;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = INVESTIGATOR_FIELD_IS_REPLACEMENT)
    public boolean isReplacement;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = INVESTIGATOR_FIELD_IS_DEAD)
    public boolean isDead;

    @DatabaseField(dataType = DataType.INTEGER, columnName = INVESTIGATOR_FIELD_EXPANSION_ID)
    public int expansionID;

    @DatabaseField(dataType = DataType.INTEGER, columnName = INVESTIGATOR_FIELD_SPECIALIZATION_ID)
    public int specialization;

    public InvestigatorOld() {
    }

    public String getName() {
        if (Localization.getInstance().isRusLocale()) return nameRU;
        else return nameEN;
    }

    public long getId() {
        return id;
    }

    public ru.mgusev.eldritchhorror.model.Investigator convert() {
        ru.mgusev.eldritchhorror.model.Investigator convertedInvestigator = new ru.mgusev.eldritchhorror.model.Investigator();
        convertedInvestigator.setId(id);
        convertedInvestigator.setGameId(gameId);
        convertedInvestigator.setImageResource(imageResource);
        convertedInvestigator.setIsMale(isMale);
        convertedInvestigator.setNameEN(nameEN);
        convertedInvestigator.setNameRU(nameRU);
        convertedInvestigator.setOccupationEN(occupationEN);
        convertedInvestigator.setOccupationRU(occupationRU);
        convertedInvestigator.setIsStarting(isStarting);
        convertedInvestigator.setIsReplacement(isReplacement);
        convertedInvestigator.setIsDead(isDead);
        convertedInvestigator.setExpansionID(expansionID);
        convertedInvestigator.setSpecialization(specialization);
        return convertedInvestigator;
    }
}