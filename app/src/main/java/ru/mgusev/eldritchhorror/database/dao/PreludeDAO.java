package ru.mgusev.eldritchhorror.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Prelude;

@Dao
public interface PreludeDAO {

    @Query("SELECT * FROM preludes WHERE _id = 0 UNION ALL SELECT * FROM (SELECT * FROM preludes WHERE _id > 0 ORDER BY name_en)")
    List<Prelude> getAllEN();

    @Query("SELECT * FROM preludes WHERE _id = 0 UNION ALL SELECT * FROM (SELECT * FROM preludes WHERE _id > 0 ORDER BY name_ru)")
    List<Prelude> getAllRU();

    @Query("SELECT * FROM preludes WHERE _id IS :id")
    Prelude getPreludeByID(int id);

    @Query("SELECT * FROM preludes WHERE name_en IS :name OR name_ru IS :name")
    Prelude getPreludeByName(String name);

    @Query("SELECT * FROM preludes WHERE defeat = 1 ORDER BY name_en")
    List<Prelude> getDefeatPreludesEN();

    @Query("SELECT * FROM preludes WHERE defeat = 1 ORDER BY name_ru")
    List<Prelude> getDefeatPreludesRU();
}
