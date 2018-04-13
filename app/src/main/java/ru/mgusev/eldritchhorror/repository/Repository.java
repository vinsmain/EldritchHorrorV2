package ru.mgusev.eldritchhorror.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.database.HelperFactory;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.Prelude;
import ru.mgusev.eldritchhorror.presentation.App;

public class Repository {

    private static Repository repository;

    private Game game;
    private PublishSubject<AncientOne> ancientOne;

    private Repository() {
        ancientOne = PublishSubject.create();
    }

    public static synchronized Repository getInstance() {
        if (repository == null) repository = new Repository();
        return repository;
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
            ancientOne = HelperFactory.getStaticHelper().getAncientOneDAO().getAncienOneByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ancientOne;
    }

    public String getExpansionIconNameByAncientOneId(int id) {
        String name = null;
        try {
            name = HelperFactory.getStaticHelper().getExpansionDAO().getImageResourceByAncientOneID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    // StartDataFragment

    public List<String> getPlayersCountArray() {
        return Arrays.asList(App.getContext().getResources().getStringArray(R.array.playersCountArray));
    }

    public List<String> getAncientOneNameList() {
        List<String> ancientOneNameList = new ArrayList<>();
        try {
            ancientOneNameList.addAll(HelperFactory.getStaticHelper().getAncientOneDAO().getAncientOneNameList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ancientOneNameList;
    }

    public List<String> getPreludeNameList() {
        List<String> preludeNameList = new ArrayList<>();
        try {
            preludeNameList.addAll(HelperFactory.getStaticHelper().getPreludeDAO().getPreludeNameList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preludeNameList;
    }

    public String getAncientOneNameByID(int id) {
        String name = null;
        try {
            name = HelperFactory.getStaticHelper().getAncientOneDAO().getAncientOneNameByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getPreludeNameByID(int id) {
        String name = null;
        try {
            name = HelperFactory.getStaticHelper().getPreludeDAO().getPreludeNameByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public AncientOne getAncientOneByName(String name) {
        AncientOne ancientOne = new AncientOne();
        try {
            ancientOne = HelperFactory.getStaticHelper().getAncientOneDAO().getAncientOneByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ancientOne;
    }

    public Prelude getPreludeByName(String name) {
        Prelude prelude = new Prelude();
        try {
            prelude = HelperFactory.getStaticHelper().getPreludeDAO().getPreludeByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prelude;
    }

    // InvestigatorChoiceFragment

    public List<Investigator> getInvestigatorList() {
        List<Investigator> investigatorsList = new ArrayList<>();
        try {
            investigatorsList.addAll(HelperFactory.getStaticHelper().getInvestigatorDAO().getAllInvestigatorsLocal());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return investigatorsList;
    }
}