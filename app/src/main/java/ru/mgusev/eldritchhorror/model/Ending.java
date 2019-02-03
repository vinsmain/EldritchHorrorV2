package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "forgotten_endings")
public class Ending {

    public static final String ENDING_FIELD_ID = "_id";
    public static final String ENDING_FIELD_ANCIENT_ONE_ID = "ancient_one_id";
    public static final String ENDING_FIELD_VICTORY = "victory";
    public static final String ENDING_FIELD_DICE_VALUE = "dice_value";
    public static final String ENDING_FIELD_CONDITION_1_TEXT_EN = "condition_1_text_en";
    public static final String ENDING_FIELD_CONDITION_1_TEXT_RU = "condition_1_text_ru";
    public static final String ENDING_FIELD_CONDITION_2_TEXT_EN = "condition_2_text_en";
    public static final String ENDING_FIELD_CONDITION_2_TEXT_RU = "condition_2_text_ru";
    public static final String ENDING_FIELD_HEADER_EN = "header_en";
    public static final String ENDING_FIELD_HEADER_RU = "header_ru";
    public static final String ENDING_FIELD_TEXT_EN = "text_en";
    public static final String ENDING_FIELD_TEXT_RU = "text_ru";

    @PrimaryKey
    @ColumnInfo(name = ENDING_FIELD_ID)
    private int id;

    @ColumnInfo(name = ENDING_FIELD_ANCIENT_ONE_ID)
    private int ancientOneID;

    @ColumnInfo(name = ENDING_FIELD_VICTORY)
    private boolean victory;

    @ColumnInfo(name = ENDING_FIELD_DICE_VALUE)
    private int diceValue;

    @ColumnInfo(name = ENDING_FIELD_CONDITION_1_TEXT_EN)
    private String condition1EN;

    @ColumnInfo(name = ENDING_FIELD_CONDITION_1_TEXT_RU)
    private String condition1RU;

    @ColumnInfo(name = ENDING_FIELD_CONDITION_2_TEXT_EN)
    private String condition2EN;

    @ColumnInfo(name = ENDING_FIELD_CONDITION_2_TEXT_RU)
    private String condition2RU;

    @ColumnInfo(name = ENDING_FIELD_HEADER_EN)
    private String headerEN;

    @ColumnInfo(name = ENDING_FIELD_HEADER_RU)
    private String headerRU;

    @ColumnInfo(name = ENDING_FIELD_TEXT_EN)
    private String textEN;

    @ColumnInfo(name = ENDING_FIELD_TEXT_RU)
    private String textRU;

    public Ending() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAncientOneID() {
        return ancientOneID;
    }

    public void setAncientOneID(int ancientOneID) {
        this.ancientOneID = ancientOneID;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }

    public String getCondition1EN() {
        return condition1EN;
    }

    public void setCondition1EN(String condition1EN) {
        this.condition1EN = condition1EN;
    }

    public String getCondition1RU() {
        return condition1RU;
    }

    public void setCondition1RU(String condition1RU) {
        this.condition1RU = condition1RU;
    }

    public String getCondition2EN() {
        return condition2EN;
    }

    public void setCondition2EN(String condition2EN) {
        this.condition2EN = condition2EN;
    }

    public String getCondition2RU() {
        return condition2RU;
    }

    public void setCondition2RU(String condition2RU) {
        this.condition2RU = condition2RU;
    }

    public String getHeaderEN() {
        return headerEN;
    }

    public void setHeaderEN(String headerEN) {
        this.headerEN = headerEN;
    }

    public String getHeaderRU() {
        return headerRU;
    }

    public void setHeaderRU(String headerRU) {
        this.headerRU = headerRU;
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

    public String getCondition1() {
        if (Localization.getInstance().isRusLocale()) return condition1RU;
        else return condition1RU;
    }

    public String getCondition2() {
        if (Localization.getInstance().isRusLocale()) return condition2RU;
        else return condition2RU;
    }

    public String getHeader() {
        if (Localization.getInstance().isRusLocale()) return headerRU;
        else return headerRU;
    }

    public String getText() {
        if (Localization.getInstance().isRusLocale()) return textRU;
        else return textRU;
    }
}