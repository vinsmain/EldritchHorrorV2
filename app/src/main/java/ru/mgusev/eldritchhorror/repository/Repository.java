package ru.mgusev.eldritchhorror.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.database.StaticDataDB;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Prelude;
import ru.mgusev.eldritchhorror.app.AppModule;

@Module (includes = AppModule.class)
public class Repository {

    private final Context context;
    private final PublishSubject<AncientOne> ancientOne;
    private final PublishSubject<Integer> score;
    private final StaticDataDB staticDataDB;

    private Game game;

    @Inject
    public Repository(Context context, StaticDataDB staticDataDB, Game game) {
        this.context = context;
        this.staticDataDB = staticDataDB;
        this.game = game;
        System.out.println(game);
        ancientOne = PublishSubject.create();
        score = PublishSubject.create();
    }

    public Game getGame() {
        if (game == null) game = new Game();
        return game;
    }

    public void clearGame() {
        game = null;
    }

    private AncientOne getCurrentAncientOne() {
        AncientOne currentAncientOne = getAncientOne(getAncientOneNameList().get(0));
        if (getGame().getAncientOneID() != -1) currentAncientOne = getAncientOne(getGame().getAncientOneID());
        return currentAncientOne;
    }

    public Observable<AncientOne> getObservableAncientOne() {
        return ancientOne;
    }

    public void setAncientOneId(int id) {
        game.setAncientOneID(id);
        ancientOne.onNext(getCurrentAncientOne());
    }

    public Observable<Integer> getObservableScore() {
        return score;
    }

    public void setResult(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount) {
        game.setGatesCount(gatesCount);
        game.setMonstersCount(monstersCount);
        game.setCurseCount(curseCount);
        game.setRumorsCount(rumorsCount);
        game.setCluesCount(cluesCount);
        game.setBlessedCount(blessedCount);
        game.setDoomCount(doomCount);
        game.setScore(gatesCount + (int)Math.ceil(monstersCount / 3.0f) + curseCount + rumorsCount * 3 - (int)Math.ceil(cluesCount / 3.0f) - blessedCount - doomCount);
        scoreOnNext();
    }

    public void scoreOnNext() {
        score.onNext(game.getScore());
    }

    public AncientOne getAncientOne(int id) {
        return staticDataDB.ancientOneDAO().getAncientOneByID(id);
    }

    public String getExpansionIconNameByAncientOneId(int id) {
        return staticDataDB.expansionDAO().getExpansionByID(getAncientOne(id).getExpansionID()).getImageResource();
    }

    // StartDataFragment

    public List<String> getPlayersCountArray() {
        return Arrays.asList(context.getResources().getStringArray(R.array.playersCountArray));
    }

    public List<String> getAncientOneNameList() {
        List<String> ancientOneNameList = new ArrayList<>();
        List<AncientOne> ancientOneList = staticDataDB.ancientOneDAO().getAll();
        for (AncientOne ancientOne : ancientOneList) {
            ancientOneNameList.add(ancientOne.getName());
        }
        Collections.sort(ancientOneNameList);
        return ancientOneNameList;
    }

    public List<String> getPreludeNameList() {
        List<String> preludeNameList = new ArrayList<>();
        List<Prelude> preludeList = staticDataDB.preludeDAO().getAll();
        for (Prelude prelude : preludeList) {
            preludeNameList.add(prelude.getName());
        }
        Collections.sort(preludeNameList);
        return preludeNameList;
    }

    public Prelude getPrelude(int id) {
        return staticDataDB.preludeDAO().getPreludeByID(id);
    }

    public AncientOne getAncientOne(String name) {
        return staticDataDB.ancientOneDAO().getAncientOneByName(name);
    }

    public Prelude getPrelude(String name) {
        return staticDataDB.preludeDAO().getPreludeByName(name);
    }

    // InvestigatorChoiceFragment

    public List<Investigator> getInvestigatorList() {
        return staticDataDB.investigatorDAO().getAll();
    }

    public List<Expansion> getExpansionList() {
        return staticDataDB.expansionDAO().getAll();
    }
}