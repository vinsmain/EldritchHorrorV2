package ru.mgusev.eldritchhorror.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Card;

@Dao
public interface CardDAO {

    @Query("SELECT cards.* FROM cards LEFT JOIN cards_expansions ON cards._id = cards_expansions.card_id " +
            "WHERE cards_expansions.expansion_id IN (:expansionIdList) AND type_id IS :typeId GROUP BY card_id")
    List<Card> getAll(List<Integer> expansionIdList, int typeId);

    @Query("SELECT cards.* FROM cards LEFT JOIN cards_expansions ON cards._id = cards_expansions.card_id LEFT JOIN card_types ON cards.type_id = card_types._id " +
            "WHERE cards_expansions.expansion_id IN (:expansionIdList) AND card_types.category_resource_id IS :category GROUP BY cards.name_resource_id")
    List<Card> getAllByCategory(List<Integer> expansionIdList, String category);

    @Query("SELECT SUM(card_count) FROM cards_expansions WHERE expansion_id IN (:expansionIdList) AND card_id = :cardId")
    int getCount(List<Integer> expansionIdList, int cardId);
}
