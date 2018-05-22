package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Investigator;

@Dao
public interface InvestigatorDAO {

    @Query("SELECT * FROM investigators ORDER BY expansion_id, name_en")
    List<Investigator> getAllEN();

    @Query("SELECT * FROM investigators ORDER BY expansion_id, name_ru")
    List<Investigator> getAllRU();
}
