package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import ru.mgusev.eldritchhorror.model.Expansion;

@Dao
public interface ExpansionDAO {

    @Query("SELECT * FROM expansions WHERE _id IS :id")
    Expansion getExpansionByID(int id);
}
