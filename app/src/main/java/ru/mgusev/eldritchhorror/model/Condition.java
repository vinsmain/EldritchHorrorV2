package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "conditions")
public class Condition {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "name_resource_id")
    private String nameResourceID;

    @ColumnInfo(name = "type_id")
    private int typeID;

    @ColumnInfo(name = "expansion_id")
    private int expansionID;

    public Condition() {
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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getExpansionID() {
        return expansionID;
    }

    public void setExpansionID(int expansionID) {
        this.expansionID = expansionID;
    }
}
