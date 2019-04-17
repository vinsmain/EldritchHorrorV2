package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Card;

@Dao
public interface CardDAO {

    @Query("SELECT * FROM cards LEFT JOIN cards_expansions ON cards._id = cards_expansions.card_id " +
            "WHERE cards_expansions.expansion_id IN (:expansionIdList) AND type_id IS :typeId GROUP BY card_id")
    List<Card> getAll(List<Integer> expansionIdList, int typeId);
}
