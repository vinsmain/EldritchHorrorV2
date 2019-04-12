package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "condition_types")
public class ConditionType {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "name_resource_id")
    private String nameResourceID;

    public ConditionType() {
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
}
