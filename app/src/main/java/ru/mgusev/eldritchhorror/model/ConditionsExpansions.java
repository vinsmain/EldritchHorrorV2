package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "conditions_expansions", primaryKeys = {"condition_id","expansion_id"})
public class ConditionsExpansions {

    @ColumnInfo(name = "condition_id")
    private int conditionID;

    @ColumnInfo(name = "expansion_id")
    private int expansionID;

    public ConditionsExpansions() {
    }

    public int getConditionID() {
        return conditionID;
    }

    public void setConditionID(int conditionID) {
        this.conditionID = conditionID;
    }

    public int getExpansionID() {
        return expansionID;
    }

    public void setExpansionID(int expansionID) {
        this.expansionID = expansionID;
    }
}
