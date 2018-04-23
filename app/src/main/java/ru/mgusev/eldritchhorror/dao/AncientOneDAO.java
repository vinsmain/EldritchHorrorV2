package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.AncientOne;

@Dao
public interface AncientOneDAO {

    @Query("SELECT * FROM ancient_ones")
    List<AncientOne> getAll();

    @Query("SELECT * FROM ancient_ones WHERE _id IS :id")
    AncientOne getAncientOneByID(int id);

    @Query("SELECT * FROM ancient_ones WHERE name_en IS :name OR name_ru IS :name")
    AncientOne getAncientOneByName(String name);
}
