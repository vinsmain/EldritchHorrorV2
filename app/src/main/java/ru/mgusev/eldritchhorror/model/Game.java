package ru.mgusev.eldritchhorror.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DatabaseTable(tableName = "games")
public class Game implements Parcelable {

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
    private long id;

    @DatabaseField(dataType = DataType.LONG, columnName = GAME_FIELD_DATE)
    private long date;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_ANCIENT_ONE_ID)
    private int ancientOneID;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_PLAYERS_COUNT)
    private int playersCount;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_SIMPLE_MYTHS)
    private boolean isSimpleMyths;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_NORMAL_MYTHS)
    private boolean isNormalMyths;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_HARD_MYTHS)
    private boolean isHardMyths;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_STARTING_RUMOR)
    private boolean isStartingRumor;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_WIN_GAME)
    private boolean isWinGame;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_DEFEAT_BY_ELIMINATION)
    private boolean isDefeatByElimination;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_DEFEAT_BY_MYTHOS_DEPLETION)
    private boolean isDefeatByMythosDepletion;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = GAME_FIELD_DEFEAT_BY_AWAKENED_ANCIENT_ONE)
    private boolean isDefeatByAwakenedAncientOne;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_GATES_COUNT)
    private int gatesCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_MONSTERS_COUNT)
    private int monstersCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_CURSE_COUNT)
    private int curseCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_RUMORS_COUNT)
    private int rumorsCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_CLUES_COUNT)
    private int cluesCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_BLESSED_COUNT)
    private int blessedCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_DOOM_COUNT)
    private int doomCount;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_SCORE)
    private int score;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_PRELUDE_ID)
    private int preludeID;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_SOLVED_MYSTERIES_COUNT)
    private int solvedMysteriesCount;

    @DatabaseField(dataType = DataType.STRING, columnName = GAME_FIELD_USER_ID)
    private String userID;

    @DatabaseField(dataType = DataType.LONG, columnName = GAME_FIELD_LAST_MODIFIED)
    private long lastModified;

    @DatabaseField(dataType = DataType.INTEGER, columnName = GAME_FIELD_ADVENTURE_ID)
    private int adventureID;

    private List<Investigator> invList;

    public Game() {
        id = -1;
        date = (new Date()).getTime();
        ancientOneID = -1;
        playersCount = 1;
        isSimpleMyths = true;
        isNormalMyths = true;
        isHardMyths = true;
        isStartingRumor = false;
        isWinGame = false;
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

    private Game(Parcel in) {
        id = in.readLong();
        date = in.readLong();
        ancientOneID = in.readInt();
        playersCount = in.readInt();
        isSimpleMyths = in.readByte() != 0;
        isNormalMyths = in.readByte() != 0;
        isHardMyths = in.readByte() != 0;
        isStartingRumor = in.readByte() != 0;
        isWinGame = in.readByte() != 0;
        isDefeatByElimination = in.readByte() != 0;
        isDefeatByMythosDepletion = in.readByte() != 0;
        isDefeatByAwakenedAncientOne = in.readByte() != 0;
        gatesCount = in.readInt();
        monstersCount = in.readInt();
        curseCount = in.readInt();
        rumorsCount = in.readInt();
        cluesCount = in.readInt();
        blessedCount = in.readInt();
        doomCount = in.readInt();
        score = in.readInt();
        preludeID = in.readInt();
        solvedMysteriesCount = in.readInt();
        userID = in.readString();
        lastModified = in.readLong();
        adventureID = in.readInt();
        invList = in.createTypedArrayList(Investigator.CREATOR);
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(date);
        parcel.writeInt(ancientOneID);
        parcel.writeInt(playersCount);
        parcel.writeByte((byte) (isSimpleMyths ? 1 : 0));
        parcel.writeByte((byte) (isNormalMyths ? 1 : 0));
        parcel.writeByte((byte) (isHardMyths ? 1 : 0));
        parcel.writeByte((byte) (isStartingRumor ? 1 : 0));
        parcel.writeByte((byte) (isWinGame ? 1 : 0));
        parcel.writeByte((byte) (isDefeatByElimination ? 1 : 0));
        parcel.writeByte((byte) (isDefeatByMythosDepletion ? 1 : 0));
        parcel.writeByte((byte) (isDefeatByAwakenedAncientOne ? 1 : 0));
        parcel.writeInt(gatesCount);
        parcel.writeInt(monstersCount);
        parcel.writeInt(curseCount);
        parcel.writeInt(rumorsCount);
        parcel.writeInt(cluesCount);
        parcel.writeInt(blessedCount);
        parcel.writeInt(doomCount);
        parcel.writeInt(score);
        parcel.writeInt(preludeID);
        parcel.writeInt(solvedMysteriesCount);
        parcel.writeString(userID);
        parcel.writeLong(lastModified);
        parcel.writeInt(adventureID);
        parcel.writeTypedList(invList);
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

    public boolean isSimpleMyths() {
        return isSimpleMyths;
    }

    public void setSimpleMyths(boolean simpleMyths) {
        isSimpleMyths = simpleMyths;
    }

    public boolean isNormalMyths() {
        return isNormalMyths;
    }

    public void setNormalMyths(boolean normalMyths) {
        isNormalMyths = normalMyths;
    }

    public boolean isHardMyths() {
        return isHardMyths;
    }

    public void setHardMyths(boolean hardMyths) {
        isHardMyths = hardMyths;
    }

    public boolean isStartingRumor() {
        return isStartingRumor;
    }

    public void setStartingRumor(boolean startingRumor) {
        isStartingRumor = startingRumor;
    }

    public boolean isWinGame() {
        return isWinGame;
    }

    public void setWinGame(boolean winGame) {
        isWinGame = winGame;
    }

    public boolean isDefeatByElimination() {
        return isDefeatByElimination;
    }

    public void setDefeatByElimination(boolean defeatByElimination) {
        isDefeatByElimination = defeatByElimination;
    }

    public boolean isDefeatByMythosDepletion() {
        return isDefeatByMythosDepletion;
    }

    public void setDefeatByMythosDepletion(boolean defeatByMythosDepletion) {
        isDefeatByMythosDepletion = defeatByMythosDepletion;
    }

    public boolean isDefeatByAwakenedAncientOne() {
        return isDefeatByAwakenedAncientOne;
    }

    public void setDefeatByAwakenedAncientOne(boolean defeatByAwakenedAncientOne) {
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
