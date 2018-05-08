package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Prelude;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class StartDataPresenter extends MvpPresenter<StartDataView> {

    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    @Inject
    Repository repository;
    private Calendar date;
    private List<String> playersCountList;
    //private List<String> ancientOneNameList;
    //private List<String> preludeNameList;
    private CompositeDisposable expansionSubscribe;

    private AncientOne currentAncientOne;
    private Prelude currentPrelude;
    private int currentPlayersCount;

    private List<AncientOne> ancientOneList;
    private List<Prelude> preludeList;


    public StartDataPresenter() {
        App.getComponent().inject(this);

        //expansionSubscribe = new CompositeDisposable();
        //expansionSubscribe.add(repository.getExpansionPublish().subscribe(this::initSpinners));
        //repository.expansionOnNext();
        date = Calendar.getInstance();
        playersCountList = repository.getPlayersCountArray();
        ancientOneList = repository.getAncientOneList();
        preludeList = repository.getPreludeList();

        date.setTimeInMillis(repository.getGame().getDate());
    }

    /*private void initSpinners(List<Expansion> expansionList) {
        ancientOneNameList = repository.getAncientOneNameList();
        preludeNameList = repository.getPreludeNameList();
        getViewState().initAncientOneSpinner(ancientOneNameList);
        getViewState().initPreludeSpinner(preludeNameList);
        setSpinnerPosition();
    }*/

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        setDateToField();
        getViewState().initAncientOneSpinner(getAncientOneNameList());
        getViewState().initPreludeSpinner(getPreludeNameList());
        getViewState().initPlayersCountSpinner(playersCountList);
        setSpinnerPosition();
        setSwitchValue();
        System.out.println("FIRST ATTACH");
    }

    private List<String> getAncientOneNameList() {
        List<String> list = new ArrayList<>();
        for (AncientOne ancientOne : ancientOneList) {
            if (repository.getExpansion(ancientOne.getExpansionID()).isEnable())
                list.add(ancientOne.getName());
        }
        Collections.sort(list);
        return list;
    }

    private List<String> getPreludeNameList() {
        List<String> list = new ArrayList<>();
        for (Prelude prelude : preludeList) {
            if (repository.getExpansion(prelude.getExpansionID()).isEnable())
                list.add(prelude.getName());
        }
        Collections.sort(list);
        return list;
    }

    public void showDatePicker() {
        getViewState().showDatePicker(date);
    }

    public void setDate(int year, int month, int day) {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
        repository.getGame().setDate(date.getTimeInMillis());
    }

    public void setDateToField() {
        getViewState().setDateToField(date);
    }

    public void dismissDatePicker() {
        getViewState().dismissDatePicker();
    }

    public void setCurrentPlayersCount(String value) {
        currentPlayersCount = Integer.parseInt(value);
    }

    public void setCurrentAncientOne(String value) {
        currentAncientOne = repository.getAncientOne(value);
    }

    public void setCurrentPrelude(String value) {
        currentPrelude = repository.getPrelude(value);
    }

    public void setSpinnerPosition() {
        int ancientOnePosition = 0;
        if (repository.getGame().getAncientOneID() != -1)
            ancientOnePosition = getAncientOneNameList().indexOf(repository.getAncientOne(repository.getGame().getAncientOneID()).getName());
        getViewState().setSpinnerPosition(ancientOnePosition,
                getPreludeNameList().indexOf(repository.getPrelude(repository.getGame().getPreludeID()).getName()),
                playersCountList.indexOf(String.valueOf(repository.getGame().getPlayersCount())));
    }

    public void setSwitchValue() {
        Game game = repository.getGame();
        getViewState().setSwitchValue(game.isSimpleMyths(), game.isNormalMyths(), game.isHardMyths(), game.isStartingRumor());
    }

    public void setSwitchValueToGame(boolean easyMythosValue, boolean normalMythosValue, boolean hardMythosValue, boolean startingRumorValue) {
        repository.getGame().setSimpleMyths(easyMythosValue);
        repository.getGame().setNormalMyths(normalMythosValue);
        repository.getGame().setHardMyths(hardMythosValue);
        repository.getGame().setStartingRumor(startingRumorValue);
    }

    @Override
    public void onDestroy() {
        repository.getGame().setPlayersCount(currentPlayersCount);
        repository.getGame().setAncientOneID(currentAncientOne.getId());
        repository.getGame().setPreludeID(currentPrelude.getId());
        //repository.clearGame();
        super.onDestroy();
    }
}