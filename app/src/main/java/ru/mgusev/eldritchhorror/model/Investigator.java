package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import timber.log.Timber;

@Entity(tableName = "investigators")
public class Investigator {

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

    @PrimaryKey
    @ColumnInfo(name = INVESTIGATOR_FIELD_ID)
    private long id;

    @ColumnInfo(name = INVESTIGATOR_FIELD_GAME_ID)
    private long gameId;

    @ColumnInfo(name = INVESTIGATOR_FIELD_IMAGE_RESOURCE)
    private String imageResource;

    @ColumnInfo(name = INVESTIGATOR_FIELD_IS_MALE)
    private boolean isMale;

    @ColumnInfo(name = INVESTIGATOR_FIELD_NAME_EN)
    private String nameEN;

    @ColumnInfo(name = INVESTIGATOR_FIELD_NAME_RU)
    private String nameRU;

    @ColumnInfo(name = INVESTIGATOR_FIELD_OCCUPATION_EN)
    private String occupationEN;

    @ColumnInfo(name = INVESTIGATOR_FIELD_OCCUPATION_RU)
    private String occupationRU;

    @ColumnInfo(name = INVESTIGATOR_FIELD_IS_STARTING)
    private boolean isStarting;

    @ColumnInfo(name = INVESTIGATOR_FIELD_IS_REPLACEMENT)
    private boolean isReplacement;

    @ColumnInfo(name = INVESTIGATOR_FIELD_IS_DEAD)
    private boolean isDead;

    @ColumnInfo(name = INVESTIGATOR_FIELD_EXPANSION_ID)
    private int expansionID;

    @ColumnInfo(name = INVESTIGATOR_FIELD_SPECIALIZATION_ID)
    private int specialization;

    public Investigator() {
    }

    public Investigator(Investigator investigator) {
        this.id = investigator.id;
        this.gameId = investigator.gameId;
        this.imageResource = investigator.imageResource;
        this.isMale = investigator.isMale;
        this.nameEN = investigator.nameEN;
        this.nameRU = investigator.nameRU;
        this.occupationEN = investigator.occupationEN;
        this.occupationRU = investigator.occupationRU;
        this.isStarting = investigator.isStarting;
        this.isReplacement = investigator.isReplacement;
        this.isDead = investigator.isDead;
        this.expansionID = investigator.expansionID;
        this.specialization = investigator.specialization;
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

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(boolean male) {
        isMale = male;
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

    public String getOccupationEN() {
        return occupationEN;
    }

    public void setOccupationEN(String occupationEN) {
        this.occupationEN = occupationEN;
    }

    public String getOccupationRU() {
        return occupationRU;
    }

    public void setOccupationRU(String occupationRU) {
        this.occupationRU = occupationRU;
    }

    public boolean getIsStarting() {
        return isStarting;
    }

    public void setIsStarting(boolean starting) {
        isStarting = starting;
    }

    public boolean getIsReplacement() {
        return isReplacement;
    }

    public void setIsReplacement(boolean replacement) {
        isReplacement = replacement;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean dead) {
        isDead = dead;
    }

    public int getExpansionID() {
        return expansionID;
    }

    public void setExpansionID(int expansionID) {
        this.expansionID = expansionID;
    }

    public int getSpecialization() {
        return specialization;
    }

    public void setSpecialization(int specialization) {
        this.specialization = specialization;
    }

    public String getName() {
        if (Localization.getInstance().isRusLocale()) return nameRU;
        else return nameEN;
    }

    public void setName(String name) {
        // For fix Firebase ClassMapper alert
        // W/ClassMapper: No setter/field for name found on class ru.mgusev.eldritchhorror.model.InvestigatorOld (fields/setters are case sensitive!)
    }

    public String getOccupation() {
        if (Localization.getInstance().isRusLocale()) return occupationRU;
        else return occupationEN;
    }

    public void setOccupation(String occupation) {
        // For fix Firebase ClassMapper alert
        // W/ClassMapper: No setter/field for occupation found on class ru.mgusev.eldritchhorror.model.InvestigatorOld (fields/setters are case sensitive!)
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        Investigator investigator = new Investigator();
        if(obj instanceof Investigator) investigator = (Investigator) obj;
        return investigator.getNameEN().equals(getNameEN());
    }

    public boolean equalsTwoInvestigators(Investigator inv) {
        return getNameEN().equals(inv.getNameEN()) &&
                getOccupationEN().equals(inv.getOccupationEN()) &&
                getIsStarting() == inv.getIsStarting() &&
                getIsReplacement() == inv.getIsReplacement() &&
                getIsDead() == inv.getIsDead();
    }

    public void printLog(Investigator inv) {
        Timber.d(String.valueOf(getNameEN().equals(inv.getNameEN())));
        Timber.d(String.valueOf(getOccupationEN().equals(inv.getOccupationEN())));
        Timber.d(String.valueOf(getIsStarting() == inv.getIsStarting()));
        Timber.d(String.valueOf(getIsReplacement() == inv.getIsReplacement()));
        Timber.d(String.valueOf(getIsDead() == inv.getIsDead()));
    }
}