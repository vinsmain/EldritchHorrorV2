package ru.mgusev.eldritchhorror.repository;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.database.HelperFactory;
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

    private final HelperFactory helperFactory;

    @Inject
    public Repository(Context context, HelperFactory helperFactory) {
        this.context = context;
        this.helperFactory = helperFactory;
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
        AncientOne currentAncientOne = getAncientOneByName(getAncientOneNameList().get(0));
        if (getGame().getAncientOneID() != -1) currentAncientOne = getAncientOneByID(getGame().getAncientOneID());
        return currentAncientOne;
    }

    public Observable<AncientOne> getObservableAncientOne() {
        return ancientOne;
    }

    public void setAncientOneId(int id) {
        game.setAncientOneID(id);
        ancientOne.onNext(getCurrentAncientOne());
    }

    private AncientOne getAncientOneByID(int id) {
        AncientOne ancientOne = new AncientOne();
        try {
            ancientOne = helperFactory.getStaticHelper().getAncientOneDAO().getAncienOneByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ancientOne;
    }

    public String getExpansionIconNameByAncientOneId(int id) {
        String name = null;
        try {
            name = helperFactory.getStaticHelper().getExpansionDAO().getImageResourceByAncientOneID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    // StartDataFragment

    public List<String> getPlayersCountArray() {
        return Arrays.asList(context.getResources().getStringArray(R.array.playersCountArray));
    }

    public List<String> getAncientOneNameList() {
        List<String> ancientOneNameList = new ArrayList<>();
        try {
            ancientOneNameList.addAll(helperFactory.getStaticHelper().getAncientOneDAO().getAncientOneNameList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ancientOneNameList;
    }

    public List<String> getPreludeNameList() {
        List<String> preludeNameList = new ArrayList<>();
        try {
            preludeNameList.addAll(helperFactory.getStaticHelper().getPreludeDAO().getPreludeNameList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preludeNameList;
    }

    public String getAncientOneNameByID(int id) {
        String name = null;
        try {
            name = helperFactory.getStaticHelper().getAncientOneDAO().getAncientOneNameByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getPreludeNameByID(int id) {
        String name = null;
        try {
            name = helperFactory.getStaticHelper().getPreludeDAO().getPreludeNameByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public AncientOne getAncientOneByName(String name) {
        AncientOne ancientOne = new AncientOne();
        try {
            ancientOne = helperFactory.getStaticHelper().getAncientOneDAO().getAncientOneByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ancientOne;
    }

    public Prelude getPreludeByName(String name) {
        Prelude prelude = new Prelude();
        try {
            prelude = helperFactory.getStaticHelper().getPreludeDAO().getPreludeByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prelude;
    }

    // InvestigatorChoiceFragment

    public List<Investigator> getInvestigatorList() {
        List<Investigator> investigatorsList = new ArrayList<>();
        try {
            investigatorsList.addAll(helperFactory.getStaticHelper().getInvestigatorDAO().getAllInvestigatorsLocal());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investigatorsList;
    }
}