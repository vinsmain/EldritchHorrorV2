package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "card_types")
public class CardType {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "name_resource_id")
    private String nameResourceID;

    @ColumnInfo(name = "category_resource_id")
    private String categoryResourceID;

    public CardType() {
    }

    @Ignore
    public CardType(int id, String nameResourceID, String categoryResourceID) {
        this.id = id;
        this.nameResourceID = nameResourceID;
        this.categoryResourceID = categoryResourceID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameResourceID() {
        return nameResourceID;
    }

    public void setNameResourceID(String nameResourceID) {
        this.nameResourceID = nameResourceID;
    }

    public String getCategoryResourceID() {
        return categoryResourceID;
    }

    public void setCategoryResourceID(String categoryResourceID) {
        this.categoryResourceID = categoryResourceID;
    }
}
