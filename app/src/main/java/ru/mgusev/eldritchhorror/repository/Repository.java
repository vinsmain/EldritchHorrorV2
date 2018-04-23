package ru.mgusev.eldritchhorror.repository;

import android.content.Context;

import java.sql.SQLException;
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
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Prelude;
import ru.mgusev.eldritchhorror.app.AppModule;

@Module (includes = AppModule.class)
public class Repository {

    private final Context context;
    private Game game;
    private final PublishSubject<AncientOne> ancientOne;

    private final StaticDataDB staticDataDB;

    @Inject
    public Repository(Context context, StaticDataDB staticDataDB) {
        this.context = context;
        this.staticDataDB = staticDataDB;
        ancientOne = PublishSubject.create();
        //game = new Game();
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
}