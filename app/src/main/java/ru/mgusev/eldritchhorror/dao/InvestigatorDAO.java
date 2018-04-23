package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Investigator;

@Dao
public interface InvestigatorDAO {

    @Query("SELECT * FROM investigators")
    List<Investigator> getAll();
}
