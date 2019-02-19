package ru.mgusev.eldritchhorror.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

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
    public static final String GAME_FIELD_PARENT_ID = "parent_id";
    public static final String GAME_FIELD_COMMENT = "comment";
    public static final String GAME_FIELD_DEFEAT_BY_RUMOR = "defeat_by_rumor";
    public static final String GAME_FIELD_DEFEAT_RUMOR_ID = "defeat_rumor_id";
    public static final String GAME_FIELD_DEFEAT_BY_SURRENDER = "defeat_by_surrender";
    public static final String GAME_FIELD_TIME = "time";

    public static final int DEFAULT_DEFEAT_RUMOR_ID = 2;

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

    @ColumnInfo(name = GAME_FIELD_PARENT_ID)
    private long parentId;

    @ColumnInfo(name = GAME_FIELD_COMMENT)
    private String comment;

    @ColumnInfo(name = GAME_FIELD_DEFEAT_BY_RUMOR)
    private boolean isDefeatByRumor;

    @ColumnInfo(name = GAME_FIELD_DEFEAT_RUMOR_ID)
    private int defeatRumorID;

    @ColumnInfo(name = GAME_FIELD_DEFEAT_BY_SURRENDER)
    private boolean isDefeatBySurrender;

    @ColumnInfo(name = GAME_FIELD_TIME)
    private int time;

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
        parentId = 0;
        comment = "";
        isDefeatByRumor = false;
        defeatRumorID = DEFAULT_DEFEAT_RUMOR_ID;
        isDefeatBySurrender = false;
        time = 0;
        invList = new ArrayList<>();
    }

    public Game(Game game) {
        this.id = game.id;
        this.date = game.date;
        this.ancientOneID = game.ancientOneID;
        this.playersCount = game.playersCount;
        this.isSimpleMyths = game.isSimpleMyths;
        this.isNormalMyths = game.isNormalMyths;
        this.isHardMyths = game.isHardMyths;
        this.isStartingRumor = game.isStartingRumor;
        this.isWinGame = game.isWinGame;
        this.isDefeatByElimination = game.isDefeatByElimination;
        this.isDefeatByMythosDepletion = game.isDefeatByMythosDepletion;
        this.isDefeatByAwakenedAncientOne = game.isDefeatByAwakenedAncientOne;
        this.gatesCount = game.gatesCount;
        this.monstersCount = game.monstersCount;
        this.curseCount = game.curseCount;
        this.rumorsCount = game.rumorsCount;
        this.cluesCount = game.cluesCount;
        this.blessedCount = game.blessedCount;
        this.doomCount = game.doomCount;
        this.score = game.score;
        this.preludeID = game.preludeID;
        this.solvedMysteriesCount = game.solvedMysteriesCount;
        this.userID = game.userID;
        this.lastModified = game.lastModified;
        this.adventureID = game.adventureID;
        this.parentId = game.getParentId();
        this.comment = game.getComment();
        this.isDefeatByRumor = game.isDefeatByRumor;
        this.defeatRumorID = game.defeatRumorID;
        this.isDefeatBySurrender = game.isDefeatBySurrender;
        this.time = game.time;
        this.invList = game.invList;
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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getIsDefeatByRumor() {
        return isDefeatByRumor;
    }

    public void setIsDefeatByRumor(boolean defeatByRumor) {
        isDefeatByRumor = defeatByRumor;
    }

    public int getDefeatRumorID() {
        return defeatRumorID;
    }

    public void setDefeatRumorID(int defeatRumorID) {
        this.defeatRumorID = defeatRumorID;
    }

    public boolean getIsDefeatBySurrender() {
        return isDefeatBySurrender;
    }

    public void setIsDefeatBySurrender(boolean defeatBySurrender) {
        isDefeatBySurrender = defeatBySurrender;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Investigator> getInvList() {
        return invList;
    }

    public void setInvList(List<Investigator> invList) {
        this.invList = invList;
    }

    public void clearResultValuesIfDefeat() {
        if (!getIsWinGame()) {
            setGatesCount(0);
            setMonstersCount(0);
            setCurseCount(0);
            setRumorsCount(0);
            setCluesCount(0);
            setBlessedCount(0);
            setDoomCount(0);
            setScore(0);
        }
    }

    public void trimCommentText() {
        setComment(getComment().trim());
    }

    public void clearDefeatRumorID() {
        if (!getIsDefeatByRumor()) setDefeatRumorID(DEFAULT_DEFEAT_RUMOR_ID);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        Game game = new Game();
        if(obj instanceof Game) game = (Game) obj;
        String comment1 = getComment();
        String comment2 = game.getComment();
        if (comment1 == null) comment1 = "";
        if (comment2 == null) comment2 = "";
        return getId() == game.getId() &&
                getDate() == game.getDate() &&
                getAncientOneID() == game.getAncientOneID() &&
                getPlayersCount() == game.getPlayersCount() &&
                getIsSimpleMyths() == game.getIsSimpleMyths() &&
                getIsNormalMyths() == game.getIsNormalMyths() &&
                getIsHardMyths() == game.getIsHardMyths() &&
                getIsStartingRumor() == game.getIsStartingRumor() &&
                getIsWinGame() == game.getIsWinGame() &&
                getIsDefeatByElimination() == game.getIsDefeatByElimination() &&
                getIsDefeatByMythosDepletion() == game.getIsDefeatByMythosDepletion() &&
                getIsDefeatByAwakenedAncientOne() == game.getIsDefeatByAwakenedAncientOne() &&
                getGatesCount() == game.getGatesCount() &&
                getMonstersCount() == game.getMonstersCount() &&
                getCurseCount() == game.getCurseCount() &&
                getRumorsCount() == game.getRumorsCount() &&
                getCluesCount() == game.getCluesCount() &&
                getBlessedCount() == game.getBlessedCount() &&
                getDoomCount() == game.getDoomCount() &&
                getScore() == game.getScore() &&
                getPreludeID() == game.getPreludeID() &&
                getSolvedMysteriesCount() == game.getSolvedMysteriesCount() &&
                getLastModified() == game.getLastModified() &&
                getAdventureID() == game.getAdventureID() &&
                getParentId() == game.getParentId() &&
                comment1.equals(comment2.trim()) &&
                getIsDefeatByRumor() == game.getIsDefeatByRumor() &&
                getDefeatRumorID() == game.getDefeatRumorID() &&
                getIsDefeatBySurrender() == game.getIsDefeatBySurrender() &&
                getTime() == game.getTime() &&
                equalsInvList(getInvList(), game.getInvList());
    }

    private boolean equalsInvList(List<Investigator> invList1, List<Investigator> invList2) {
        if (invList1.size() != invList2.size()) return false;
        for (Investigator investigator1 : invList1) {
            for (Investigator investigator2 : invList2) {
                if (investigator1.equals(investigator2)) {
                    if (!investigator1.equalsTwoInvestigators(investigator2)) return false;
                }
            }
        }
        return true;
    }
    
    public void printLog(Game game) {
        Timber.tag("GAME").d(String.valueOf(getId() == game.getId()));
        Timber.tag("GAME").d(String.valueOf(getDate() == game.getDate()));
        Timber.tag("GAME").d(String.valueOf(getAncientOneID() == game.getAncientOneID()));
        Timber.tag("GAME").d(String.valueOf(getPlayersCount() == game.getPlayersCount()));
        Timber.tag("GAME").d(String.valueOf(getIsSimpleMyths() == game.getIsSimpleMyths()));
        Timber.tag("GAME").d(String.valueOf(getIsNormalMyths() == game.getIsNormalMyths()));
        Timber.tag("GAME").d(String.valueOf(getIsHardMyths() == game.getIsHardMyths()));
        Timber.tag("GAME").d(String.valueOf(getIsStartingRumor() == game.getIsStartingRumor()));
        Timber.tag("GAME").d(String.valueOf(getIsWinGame() == game.getIsWinGame()));
        Timber.tag("GAME").d(String.valueOf(getIsDefeatByElimination() == game.getIsDefeatByElimination()));
        Timber.tag("GAME").d(String.valueOf(getIsDefeatByMythosDepletion() == game.getIsDefeatByMythosDepletion()));
        Timber.tag("GAME").d(String.valueOf(getIsDefeatByAwakenedAncientOne() == game.getIsDefeatByAwakenedAncientOne()));
        Timber.tag("GAME").d(String.valueOf(getGatesCount() == game.getGatesCount()));
        Timber.tag("GAME").d(String.valueOf(getMonstersCount() == game.getMonstersCount()));
        Timber.tag("GAME").d(String.valueOf(getCurseCount() == game.getCurseCount()));
        Timber.tag("GAME").d(String.valueOf(getRumorsCount() == game.getRumorsCount()));
        Timber.tag("GAME").d(String.valueOf(getCluesCount() == game.getCluesCount()));
        Timber.tag("GAME").d(String.valueOf(getBlessedCount() == game.getBlessedCount()));
        Timber.tag("GAME").d(String.valueOf(getDoomCount() == game.getDoomCount()));
        Timber.tag("GAME").d(String.valueOf(getScore() == game.getScore()));
        Timber.tag("GAME").d(String.valueOf(getPreludeID() == game.getPreludeID()));
        Timber.tag("GAME").d(String.valueOf(getSolvedMysteriesCount() == game.getSolvedMysteriesCount()));
        Timber.tag("GAME").d(String.valueOf(getLastModified() == game.getLastModified()));
        Timber.tag("GAME").d(String.valueOf(getAdventureID() == game.getAdventureID()));
        Timber.tag("GAME").d(String.valueOf(getComment().equals(game.getComment().trim())));
        Timber.tag("GAME").d(String.valueOf(getTime() == game.getTime()));
        Timber.tag("GAME").d(String.valueOf(equalsInvList(getInvList(), game.getInvList())));
    }
}