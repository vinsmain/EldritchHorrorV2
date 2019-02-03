package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Ending;

@Dao
public interface EndingDAO {

    @Query("SELECT * FROM forgotten_endings WHERE ancient_one_id = :ancientOneId")
    List<Ending> getEndingListByAncientOneId(int ancientOneId);

    @Query("SELECT ancient_one_id FROM forgotten_endings GROUP BY ancient_one_id")
    List<Integer> getAncientOneIdList();

    @Query("SELECT condition_1_text_ru FROM forgotten_endings WHERE (ancient_one_id = :ancientOneId AND victory = :victory AND condition_1_text_ru NOT NULL) GROUP BY condition_1_text_ru")
    List<String> getCondition1List(int ancientOneId, boolean victory);

    @Query("SELECT condition_2_text_ru FROM forgotten_endings WHERE (ancient_one_id = :ancientOneId AND victory = :victory AND condition_2_text_ru NOT NULL) GROUP BY condition_2_text_ru")
    List<String> getCondition2List(int ancientOneId, boolean victory);


    @Query("SELECT condition_1_text_ru FROM forgotten_endings GROUP BY condition_1_text_ru")
    List<String> getCondition1List();

    @Query("SELECT condition_2_text_ru FROM forgotten_endings GROUP BY condition_2_text_ru")
    List<String> getCondition2List();
}