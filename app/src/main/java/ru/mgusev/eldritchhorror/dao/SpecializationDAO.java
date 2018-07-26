package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

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