package ru.mgusev.eldritchhorror.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Game;

@Dao
public interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(Game game);

    @Delete
    void deleteGame(Game game);

    @Query("SELECT * FROM games ORDER BY date ASC, _id ASC")
    List<Game> getGameListSortedDateAscending();

    @Query("SELECT * FROM games ORDER BY date DESC, _id DESC")
    List<Game> getGameListSortedDateDescending();

    @Query("SELECT * FROM games ORDER BY ancient_one_id ASC, win_game DESC, score ASC, date DESC, _id DESC")
    List<Game> getGameListSortedAncientOne();

    @Query("SELECT * FROM games ORDER BY win_game DESC, score ASC, date DESC, _id DESC")
    List<Game> getGameListSortedScoreAscending();

    @Query("SELECT * FROM games ORDER BY win_game ASC, score DESC, date ASC, _id ASC")
    List<Game> getGameListSortedScoreDescending();

    @Query("SELECT count(*) FROM games")
    int getGameCount();

    @Query("SELECT count(*) FROM games WHERE win_game = 1")
    int getVictoryGameCount();

    @Query("SELECT count(*) FROM games WHERE win_game = 0")
    int getDefeatGameCount();

    @Query("SELECT score FROM games WHERE win_game = 1 ORDER BY score ASC LIMIT 1")
    int getBestScore();

    @Query("SELECT score FROM games WHERE win_game = 1 ORDER BY score DESC LIMIT 1")
    int getWorstScore();
}
