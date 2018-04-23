package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Prelude;

@Dao
public interface PreludeDAO {

    @Query("SELECT * FROM preludes")
    List<Prelude> getAll();

    @Query("SELECT * FROM preludes WHERE _id IS :id")
    Prelude getPreludeByID(int id);

    @Query("SELECT * FROM preludes WHERE name_en IS :name OR name_ru IS :name")
    Prelude getPreludeByName(String name);
}
