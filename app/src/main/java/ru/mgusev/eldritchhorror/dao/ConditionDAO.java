package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Condition;

@Dao
public interface ConditionDAO {

    @Query("SELECT * FROM conditions")
    List<Condition> getAll();
}
