package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Rumor;

@Dao
public interface RumorDAO {

    @Query("SELECT * FROM rumors ORDER BY name_en")
    List<Rumor> getAllEN();

    @Query("SELECT * FROM rumors ORDER BY name_ru")
    List<Rumor> getAllRU();

    @Query("SELECT * FROM rumors WHERE _id IS :id")
    Rumor getRumorByID(int id);

    @Query("SELECT * FROM rumors WHERE name_en IS :name OR name_ru IS :name")
    Rumor getRumorByName(String name);
}
