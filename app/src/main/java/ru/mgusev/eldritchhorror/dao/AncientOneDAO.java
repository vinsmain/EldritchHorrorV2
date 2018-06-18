package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.AncientOne;

@Dao
public interface AncientOneDAO {

    @Query("SELECT * FROM ancient_ones ORDER BY name_ru")
    List<AncientOne> getAllRU();

    @Query("SELECT * FROM ancient_ones WHERE _id IN(:idList) ORDER BY name_ru")
    List<AncientOne> getAllRU(List<Integer> idList);

    @Query("SELECT * FROM ancient_ones ORDER BY name_en")
    List<AncientOne> getAllEN();

    @Query("SELECT * FROM ancient_ones WHERE _id IN(:idList) ORDER BY name_en")
    List<AncientOne> getAllEN(List<Integer> idList);

    @Query("SELECT * FROM ancient_ones WHERE _id IS :id")
    AncientOne getAncientOneByID(int id);

    @Query("SELECT * FROM ancient_ones WHERE name_en IS :name OR name_ru IS :name")
    AncientOne getAncientOneByName(String name);
}
