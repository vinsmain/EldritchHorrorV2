package ru.mgusev.eldritchhorror.database.oldDB;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DatabaseTable(tableName = "games")
public class GameOld {

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

    @DatabaseField(id = true, dataType = DataType.LONG, columnName = GAME_FIELD_ID)
    public long id;

    @DatabaseField(dataType = DataType.LONG, columnName = GAME_FIELD_DATE)
    public long date;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_ANCIENT_ONE_ID)
    public int ancientOneID;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_PLAYERS_COUNT)
    public int playersCount;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_SIMPLE_MYTHS)
    public boolean isSimpleMyths;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_NORMAL_MYTHS)
    public boolean isNormalMyths;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_HARD_MYTHS)
    public boolean isHardMyths;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_STARTING_RUMOR)
    public boolean isStartingRumor;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_WIN_GAME)
    public boolean isWinGame;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_DEFEAT_BY_ELIMINATION)
    public boolean isDefeatByElimination;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_DEFEAT_BY_MYTHOS_DEPLETION)
    public boolean isDefeatByMythosDepletion;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_DEFEAT_BY_AWAKENED_ANCIENT_ONE)
    public boolean isDefeatByAwakenedAncientOne;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_GATES_COUNT)
    public int gatesCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_MONSTERS_COUNT)
    public int monstersCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_CURSE_COUNT)
    public int curseCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_RUMORS_COUNT)
    public int rumorsCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_CLUES_COUNT)
    public int cluesCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_BLESSED_COUNT)
    public int blessedCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_DOOM_COUNT)
    public int doomCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_SCORE)
    public int score;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_PRELUDE_ID)
    public int preludeID;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_SOLVED_MYSTERIES_COUNT)
    public int solvedMysteriesCount;

    @DatabaseField(dataType = DataType.STRING, columnName = GAME_FIELD_USER_ID)
    public String userID;

    @DatabaseField(dataType = DataType.LONG, columnName = GAME_FIELD_LAST_MODIFIED)
    public long lastModified;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_ADVENTURE_ID)
    public int adventureID;

    public List<InvestigatorOld> invList;

    public GameOld() {
        id = -1;
        date = (new Date()).getTime();
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

    public boolean getIsDefeatByElimination() {
        return isDefeatByElimination;
    }

    public boolean getIsDefeatByMythosDepletion() {
        return isDefeatByMythosDepletion;
    }

    public boolean getIsDefeatByAwakenedAncientOne() {
        return isDefeatByAwakenedAncientOne;
    }

    public void setIsDefeatByAwakenedAncientOne(boolean defeatByAwakenedAncientOne) {
        isDefeatByAwakenedAncientOne = defeatByAwakenedAncientOne;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public void setInvList(List<InvestigatorOld> invList) {
        this.invList = invList;
    }

    public ru.mgusev.eldritchhorror.model.Game convert() {
        ru.mgusev.eldritchhorror.model.Game convertedGame = new ru.mgusev.eldritchhorror.model.Game();
        convertedGame.setId(id);
        convertedGame.setDate(date);
        convertedGame.setAncientOneID(ancientOneID);
        convertedGame.setPlayersCount(playersCount);
        convertedGame.setIsSimpleMyths(isSimpleMyths);
        convertedGame.setIsNormalMyths(isNormalMyths);
        convertedGame.setIsHardMyths(isHardMyths);
        convertedGame.setIsStartingRumor(isStartingRumor);
        convertedGame.setIsWinGame(isWinGame);
        convertedGame.setIsDefeatByElimination(isDefeatByElimination);
        convertedGame.setIsDefeatByMythosDepletion(isDefeatByMythosDepletion);
        convertedGame.setIsDefeatByAwakenedAncientOne(isDefeatByAwakenedAncientOne);
        convertedGame.setGatesCount(gatesCount);
        convertedGame.setMonstersCount(monstersCount);
        convertedGame.setCurseCount(curseCount);
        convertedGame.setRumorsCount(rumorsCount);
        convertedGame.setCluesCount(cluesCount);
        convertedGame.setBlessedCount(blessedCount);
        convertedGame.setDoomCount(doomCount);
        convertedGame.setScore(score);
        convertedGame.setPreludeID(preludeID);
        convertedGame.setSolvedMysteriesCount(solvedMysteriesCount);
        convertedGame.setUserID(userID);
        convertedGame.setLastModified(lastModified);
        convertedGame.setAdventureID(adventureID);
        convertedGame.setInvList(getConvertedInvList(invList));
        return convertedGame;
    }

    private List<ru.mgusev.eldritchhorror.model.Investigator> getConvertedInvList(List<InvestigatorOld> list) {
        List<ru.mgusev.eldritchhorror.model.Investigator> convertedList = new ArrayList<>();
        for (InvestigatorOld investigatorOld : list) {
            convertedList.add(investigatorOld.convert());
        }
        return convertedList;
    }
}
