package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;

public class StatisticsInvestigator {

    @ColumnInfo(name="name_en")
    private String name;

    @ColumnInfo(name="count(name_en)")
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
