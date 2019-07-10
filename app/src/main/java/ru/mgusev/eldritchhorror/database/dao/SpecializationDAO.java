package ru.mgusev.eldritchhorror.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Specialization;

@Dao
public interface SpecializationDAO {

    @Query("SELECT * FROM specializations")
    List<Specialization> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpecializationList(List<Specialization> list);

    @Query("SELECT * FROM specializations WHERE _id = :id")
    Specialization getSpecializationById(int id);

    @Query("SELECT count(*) FROM specializations WHERE is_enable = 1")
    int getEnabledSpecializationCount();
}