package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Condition;

@Dao
public interface ConditionDAO {

    @Query("SELECT * FROM conditions LEFT JOIN conditions_expansions ON conditions._id = conditions_expansions.condition_id " +
            "WHERE conditions_expansions.expansion_id IN (:expansionIdList) AND type_id IS :typeId GROUP BY condition_id")
    List<Condition> getAll(List<Integer> expansionIdList, int typeId);
}
