package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Ending;

@Dao
public interface EndingDAO {

    @Query("SELECT ancient_one_id FROM forgotten_endings GROUP BY ancient_one_id")
    List<Integer> getAncientOneIdList();

    @Query("SELECT condition_text_ru FROM forgotten_endings WHERE (ancient_one_id = :ancientOneId AND victory = :victory AND condition_text_ru NOT NULL) GROUP BY condition_text_ru")
    List<String> getConditionList(int ancientOneId, boolean victory);

    @Query("SELECT * FROM forgotten_endings WHERE ancient_one_id = :ancientOneId AND victory = :result AND (condition_text_ru IN (:conditionList))")
    List<Ending> getEndingListWithCondition(int ancientOneId, boolean result, List<String> conditionList);

    @Query("SELECT * FROM forgotten_endings WHERE ancient_one_id = :ancientOneId AND victory = :result AND condition_text_ru IS NULL")
    List<Ending> getEndingListWithoutCondition(int ancientOneId, boolean result);
}