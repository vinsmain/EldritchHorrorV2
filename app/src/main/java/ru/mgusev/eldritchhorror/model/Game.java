package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "games")
public class Game {

    public static final String GAME_TABLE_NAME = "games";

    public static final String GAME_FIELD_ID = "_id";
    public static final String GAME_FIELD_DATE = "date";
    public static final String GAME_FIELD_ANCIENT_ONE_ID = "ancient_one_id";
    public static final String GAME_FIELD_PLAYERS_COUNT = "players_count";
    public static final String GAME_FIELD_SIMPLE_MYTHS = "simple_myths";
    public static final String GAME_FIELD_NORMAL_MYTHS = "normal_myths";
    public static final String GAME_FIELD_HARD_MYTHS = "hard_myths";
    public static final String GAME_FIELD_STARTING_RUMOR = "starting_rumor";
    public static final String GAME_FIELD_WIN_GAME = "win_game";
    public static final String GAME_FIELD_DEFEAT_BY_ELIMINATION = "defeat_by_elimination";
    public static final String GAME_FIELD_DEFEAT_BY_MYTHOS_DEPLETION = "defeat_by_mythos_depletion";
    public static final String GAME_FIELD_DEFEAT_BY_AWAKENED_ANCIENT_ONE = "defeat_by_awakened_ancient_one";
    public static final String GAME_FIELD_GATES_COUNT = "gates_count";
    public static final String GAME_FIELD_MONSTERS_COUNT = "monsters_count";
    public static final String GAME_FIELD_CURSE_COUNT = "curse_count";
    public static final String GAME_FIELD_RUMORS_COUNT = "rumors_count";
    public static final String GAME_FIELD_CLUES_COUNT = "clues_count";
    public static final String GAME_FIELD_BLESSED_COUNT = "blessed_count";
    public static final String GAME_FIELD_DOOM_COUNT = "doom_count";
    public static final String GAME_FIELD_SCORE = "score";
    public static final String GAME_FIELD_PRELUDE_ID = "prelude_id";
    public static final String GAME_FIELD_SOLVED_MYSTERIES_COUNT = "solved_mysteries_count";
    public static final String GAME_FIELD_USER_ID = "user_id";
    public static final String GAME_FIELD_LAST_MODIFIED = "last_modified";
    public static final String GAME_FIELD_ADVENTURE_ID = "adventure_id";

    @PrimaryKey
    @ColumnInfo(name = GAME_FIELD_ID)
    private long id;

    @ColumnInfo(name = GAME_FIELD_DATE)
    private long date;

    @ColumnInfo(name = GAME_FIELD_ANCIENT_ONE_ID)
    private int ancientOneID;

    @ColumnInfo(name = GAME_FIELD_PLAYERS_COUNT)
    private int playersCount;

    @ColumnInfo(name = GAME_FIELD_SIMPLE_MYTHS)
    private boolean isSimpleMyths;

    @ColumnInfo(name = GAME_FIELD_NORMAL_MYTHS)
    private boolean isNormalMyths;

    @ColumnInfo(name = GAME_FIELD_HARD_MYTHS)
    private boolean isHardMyths;

    @ColumnInfo(name = GAME_FIELD_STARTING_RUMOR)
    private boolean isStartingRumor;

    @ColumnInfo(name = GAME_FIELD_WIN_GAME)
    private boolean isWinGame;

    @ColumnInfo(name = GAME_FIELD_DEFEAT_BY_ELIMINATION)
    private boolean isDefeatByElimination;

    @ColumnInfo(name = GAME_FIELD_DEFEAT_BY_MYTHOS_DEPLETION)
    private boolean isDefeatByMythosDepletion;

    @ColumnInfo(name = GAME_FIELD_DEFEAT_BY_AWAKENED_ANCIENT_ONE)
    private boolean isDefeatByAwakenedAncientOne;

    @ColumnInfo(name = GAME_FIELD_GATES_COUNT)
    private int gatesCount;

    @ColumnInfo(name = GAME_FIELD_MONSTERS_COUNT)
    private int monstersCount;

    @ColumnInfo(name = GAME_FIELD_CURSE_COUNT)
    private int curseCount;

    @ColumnInfo(name = GAME_FIELD_RUMORS_COUNT)
    private int rumorsCount;

    @ColumnInfo(name = GAME_FIELD_CLUES_COUNT)
    private int cluesCount;

    @ColumnInfo(name = GAME_FIELD_BLESSED_COUNT)
    private int blessedCount;

    @ColumnInfo(name = GAME_FIELD_DOOM_COUNT)
    private int doomCount;

    @ColumnInfo(name = GAME_FIELD_SCORE)
    private int score;

    @ColumnInfo(name = GAME_FIELD_PRELUDE_ID)
    private int preludeID;

    @ColumnInfo(name = GAME_FIELD_SOLVED_MYSTERIES_COUNT)
    private int solvedMysteriesCount;

    @ColumnInfo(name = GAME_FIELD_USER_ID)
    private String userID;

    @ColumnInfo(name = GAME_FIELD_LAST_MODIFIED)
    private long lastModified;

    @ColumnInfo(name = GAME_FIELD_ADVENTURE_ID)
    private int adventureID;

    @Ignore
    private List<Investigator> invList;

    public Game() {
        id = new Date().getTime();
        date = new Date().getTime();
        ancientOneID = -1;
        playersCount = 1;
        isSimpleMyths = true;
        isNormalMyths = true;
        isHardMyths = true;
        isStartingRumor = false;
        isWinGame = true;
        isDefeatByElimination = false;
        isDefeatByMythosDepletion = false;
        isDefeatByAwakenedAncientOne = true;
        gatesCount = 0;
        monstersCount = 0;
        curseCount = 0;
        rumorsCount = 0;
        cluesCount = 0;
        blessedCount = 0;
        doomCount = 0;
        score = 0;
        preludeID = 0;
        solvedMysteriesCount = 3;
        userID = null;
        lastModified = 0;
        adventureID = 0;
        invList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getAncientOneID() {
        return ancientOneID;
    }

    public void setAncientOneID(int ancientOneID) {
        this.ancientOneID = ancientOneID;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public boolean getIsSimpleMyths() {
        return isSimpleMyths;
    }

    public void setIsSimpleMyths(boolean isSimpleMyths) {
        this.isSimpleMyths = isSimpleMyths;
    }

    public boolean getIsNormalMyths() {
        return isNormalMyths;
    }

    public void setIsNormalMyths(boolean normalMyths) {
        isNormalMyths = normalMyths;
    }

    public boolean getIsHardMyths() {
        return isHardMyths;
    }

    public void setIsHardMyths(boolean hardMyths) {
        isHardMyths = hardMyths;
    }

    public boolean getIsStartingRumor() {
        return isStartingRumor;
    }

    public void setIsStartingRumor(boolean startingRumor) {
        isStartingRumor = startingRumor;
    }

    public boolean getIsWinGame() {
        return isWinGame;
    }

    public void setIsWinGame(boolean winGame) {
        isWinGame = winGame;
    }

    public boolean getIsDefeatByElimination() {
        return isDefeatByElimination;
    }

    public void setIsDefeatByElimination(boolean defeatByElimination) {
        isDefeatByElimination = defeatByElimination;
    }

    public boolean getIsDefeatByMythosDepletion() {
        return isDefeatByMythosDepletion;
    }

    public void setIsDefeatByMythosDepletion(boolean defeatByMythosDepletion) {
        isDefeatByMythosDepletion = defeatByMythosDepletion;
    }

    public boolean getIsDefeatByAwakenedAncientOne() {
        return isDefeatByAwakenedAncientOne;
    }

    public void setIsDefeatByAwakenedAncientOne(boolean defeatByAwakenedAncientOne) {
        isDefeatByAwakenedAncientOne = defeatByAwakenedAncientOne;
    }

    public int getGatesCount() {
        return gatesCount;
    }

    public void setGatesCount(int gatesCount) {
        this.gatesCount = gatesCount;
    }

    public int getMonstersCount() {
        return monstersCount;
    }

    public void setMonstersCount(int monstersCount) {
        this.monstersCount = monstersCount;
    }

    public int getCurseCount() {
        return curseCount;
    }

    public void setCurseCount(int curseCount) {
        this.curseCount = curseCount;
    }

    public int getRumorsCount() {
        return rumorsCount;
    }

    public void setRumorsCount(int rumorsCount) {
        this.rumorsCount = rumorsCount;
    }

    public int getCluesCount() {
        return cluesCount;
    }

    public void setCluesCount(int cluesCount) {
        this.cluesCount = cluesCount;
    }

    public int getBlessedCount() {
        return blessedCount;
    }

    public void setBlessedCount(int blessedCount) {
        this.blessedCount = blessedCount;
    }

    public int getDoomCount() {
        return doomCount;
    }

    public void setDoomCount(int doomCount) {
        this.doomCount = doomCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPreludeID() {
        return preludeID;
    }

    public void setPreludeID(int preludeID) {
        this.preludeID = preludeID;
    }

    public int getSolvedMysteriesCount() {
        return solvedMysteriesCount;
    }

    public void setSolvedMysteriesCount(int solvedMysteriesCount) {
        this.solvedMysteriesCount = solvedMysteriesCount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public int getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(int adventureID) {
        this.adventureID = adventureID;
    }

    public List<Investigator> getInvList() {
        return invList;
    }

    public void setInvList(List<Investigator> invList) {
        this.invList = invList;
    }
}