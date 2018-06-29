package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Game;

@Dao
public interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(Game game);

    @Delete
    void deleteGame(Game game);

    @Query("SELECT * FROM games WHERE _id = :id LIMIT 1")
    Game getGame(long id);

    @Query("SELECT * FROM games ORDER BY date ASC, _id ASC")
    List<Game> getGameListSortedDateAscending();

    @Query("SELECT * FROM games ORDER BY date DESC, _id DESC")
    List<Game> getGameListSortedDateDescending();

    @Query("SELECT * FROM games WHERE ancient_one_id = :id ORDER BY date DESC, _id DESC")
    List<Game> getGameListSortedDateDescending(int id);

    @Query("SELECT * FROM games ORDER BY ancient_one_id ASC, win_game DESC, score ASC, date DESC, _id DESC")
    List<Game> getGameListSortedAncientOne();

    @Query("SELECT * FROM games ORDER BY win_game DESC, score ASC, date DESC, _id DESC")
    List<Game> getGameListSortedScoreAscending();

    @Query("SELECT * FROM games WHERE ancient_one_id = :id ORDER BY win_game DESC, score ASC, date DESC, _id DESC")
    List<Game> getGameListSortedScoreAscending(int id);

    @Query("SELECT * FROM games ORDER BY win_game ASC, score DESC, date ASC, _id ASC")
    List<Game> getGameListSortedScoreDescending();

    @Query("SELECT count(*) FROM games")
    int getGameCount();

    @Query("SELECT count(*) FROM games WHERE win_game = 1")
    int getVictoryGameCount();

    @Query("SELECT count(*) FROM games WHERE win_game = 0")
    int getDefeatGameCount();

    @Query("SELECT count(*) FROM games WHERE ancient_one_id = :id")
    int getGameCount(int id);

    @Query("SELECT count(*) FROM games WHERE win_game = 1 AND ancient_one_id = :id")
    int getVictoryGameCount(int id);

    @Query("SELECT count(*) FROM games WHERE win_game = 0 AND ancient_one_id = :id")
    int getDefeatGameCount(int id);

    @Query("SELECT score FROM games WHERE win_game = 1 ORDER BY score ASC LIMIT 1")
    int getBestScore();

    @Query("SELECT score FROM games WHERE win_game = 1 ORDER BY score DESC LIMIT 1")
    int getWorstScore();

    @Query("SELECT ancient_one_id FROM games GROUP BY ancient_one_id")
    List<Integer> getAncientOneIdList();

    @Query("SELECT count(*) FROM games WHERE ancient_one_id = :id")
    int getAncientOneCountByID(int id);

    @Query("SELECT score FROM games WHERE win_game = 1 GROUP BY score ORDER BY score ASC")
    List<Integer> getScoreList();

    @Query("SELECT count(*) FROM games WHERE win_game = 1 AND score = :score")
    int getScoreCount(int score);

    @Query("SELECT score FROM games WHERE win_game = 1 AND ancient_one_id = :id GROUP BY score ORDER BY score ASC")
    List<Integer> getScoreList(int id);

    @Query("SELECT count(*) FROM games WHERE win_game = 1 AND score = :score AND ancient_one_id = :id")
    int getScoreCount(int score, int id);

    @Query("SELECT count(*) FROM games WHERE win_game = 0 AND defeat_by_elimination = 1")
    int getDefeatByEliminationCount();

    @Query("SELECT count(*) FROM games WHERE win_game = 0 AND defeat_by_elimination = 1 AND ancient_one_id = :id")
    int getDefeatByEliminationCount(int id);

    @Query("SELECT count(*) FROM games WHERE win_game = 0 AND defeat_by_mythos_depletion = 1")
    int getDefeatByMythosDepletionCount();

    @Query("SELECT count(*) FROM games WHERE win_game = 0 AND defeat_by_mythos_depletion = 1 AND ancient_one_id = :id")
    int getDefeatByMythosDepletionCount(int id);

    @Query("SELECT count(*) FROM games WHERE win_game = 0 AND defeat_by_awakened_ancient_one = 1")
    int getDefeatByAwakenedAncientOneCount();

    @Query("SELECT count(*) FROM games WHERE win_game = 0 AND defeat_by_awakened_ancient_one = 1 AND ancient_one_id = :id")
    int getDefeatByAwakenedAncientOneCount(int id);

    @Query("SELECT _id FROM games GROUP BY _id")
    List<Long> getGameIdList();

    @Query("SELECT _id FROM games WHERE ancient_one_id = :id GROUP BY _id")
    List<Long> getGameIdList(int id);
}