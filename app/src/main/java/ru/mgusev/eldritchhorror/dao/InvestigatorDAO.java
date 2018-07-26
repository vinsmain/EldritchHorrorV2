package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.StatisticsInvestigator;

@Dao
public interface InvestigatorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInvestigatorList(List<Investigator> list);

    @Query("SELECT * FROM investigators ORDER BY expansion_id, name_en")
    List<Investigator> getAllEN();

    @Query("SELECT * FROM investigators ORDER BY expansion_id, name_ru")
    List<Investigator> getAllRU();

    @Query("SELECT * FROM investigators WHERE game_id = :id")
    List<Investigator> getByGameID(long id);

    @Query("DELETE FROM investigators WHERE game_id = :id")
     void deleteByGameID(long id);

    @Query("SELECT * FROM investigators WHERE name_en = :name OR name_ru = :name")
    Investigator getByName(String name);

    @Query("SELECT name_en, count(name_en) FROM investigators WHERE game_id IN (:gameIdList) GROUP BY name_en ORDER BY count(name_en) DESC")
    List<StatisticsInvestigator> getStatisticsInvestigatorList(List<Long> gameIdList);
}