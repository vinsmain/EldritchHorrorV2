package ru.mgusev.eldritchhorror.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.CardType;

@Dao
public interface CardTypeDAO {

    @Query("SELECT * FROM card_types " +
            "WHERE _id IN (SELECT type_id FROM cards LEFT JOIN cards_expansions ON cards._id = cards_expansions.card_id " +
            "WHERE cards_expansions.expansion_id IN (:expansionIdList) GROUP BY type_id)")
    List<CardType> getAll(List<Integer> expansionIdList);

    @Query("SELECT * FROM card_types WHERE _id IS :id")
    CardType getCardTypeByID(int id);
}
