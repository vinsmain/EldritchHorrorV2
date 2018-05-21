package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "investigators")
public class Investigator implements Parcelable {

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

    protected Investigator(Parcel in) {
        id = in.readLong();
        gameId = in.readLong();
        imageResource = in.readString();
        isMale = in.readByte() != 0;
        nameEN = in.readString();
        nameRU = in.readString();
        occupationEN = in.readString();
        occupationRU = in.readString();
        isStarting = in.readByte() != 0;
        isReplacement = in.readByte() != 0;
        isDead = in.readByte() != 0;
        expansionID = in.readInt();
        specialization = in.readInt();
    }

    public static final Creator<Investigator> CREATOR = new Creator<Investigator>() {
        @Override
        public Investigator createFromParcel(Parcel in) {
            return new Investigator(in);
        }

        @Override
        public Investigator[] newArray(int size) {
            return new Investigator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(gameId);
        parcel.writeString(imageResource);
        parcel.writeByte((byte) (isMale ? 1 : 0));
        parcel.writeString(nameEN);
        parcel.writeString(nameRU);
        parcel.writeString(occupationEN);
        parcel.writeString(occupationRU);
        parcel.writeByte((byte) (isStarting ? 1 : 0));
        parcel.writeByte((byte) (isReplacement ? 1 : 0));
        parcel.writeByte((byte) (isDead ? 1 : 0));
        parcel.writeInt(expansionID);
        parcel.writeInt(specialization);
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

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
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

    public boolean isStarting() {
        return isStarting;
    }

    public void setStarting(boolean starting) {
        isStarting = starting;
    }

    public boolean isReplacement() {
        return isReplacement;
    }

    public void setReplacement(boolean replacement) {
        isReplacement = replacement;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
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

    public String getOccupation() {
        if (Localization.getInstance().isRusLocale()) return occupationRU;
        else return occupationEN;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        Investigator investigator = (Investigator) obj;
        return investigator.id == id;
    }
}