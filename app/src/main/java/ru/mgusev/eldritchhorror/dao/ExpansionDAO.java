package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Expansion;

@Dao
public interface ExpansionDAO {

    @Query("SELECT * FROM expansions")
    List<Expansion> getAll();

    @Query("SELECT * FROM expansions WHERE _id IS :id")
    Expansion getExpansionByID(int id);

    @Update
    void updateExpansion(Expansion expansion);
}
