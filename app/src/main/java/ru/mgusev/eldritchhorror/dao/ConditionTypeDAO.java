package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.ConditionType;

@Dao
public interface ConditionTypeDAO {

    @Query("SELECT * from condition_types " +
            "WHERE _id IN (SELECT type_id FROM conditions LEFT JOIN conditions_expansions ON conditions._id = conditions_expansions.condition_id " +
            "WHERE conditions_expansions.expansion_id IN (:expansionIdList) GROUP BY type_id)")
    List<ConditionType> getAll(List<Integer> expansionIdList);
}
