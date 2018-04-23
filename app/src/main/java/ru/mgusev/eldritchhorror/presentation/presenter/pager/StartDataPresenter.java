package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class StartDataPresenter extends MvpPresenter<StartDataView> {

    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    @Inject
    Repository repository;
    private Calendar date;
    private List<String> playersCountList;
    private List<String> ancientOneList;
    private List<String> preludeList;

    public StartDataPresenter() {
        App.getComponent().inject(this);
        
        date = Calendar.getInstance();
        ancientOneList = repository.getAncientOneNameList();
        preludeList = repository.getPreludeNameList();
        playersCountList = repository.getPlayersCountArray();

        date.setTimeInMillis(repository.getGame().getDate());
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        setDateToField();
        getViewState().initAncientOneSpinner(ancientOneList);
        getViewState().initPreludeSpinner(preludeList);
        getViewState().initPlayersCountSpinner(playersCountList);
        setSpinnerPosition();
        setSwitchValue();
        System.out.println("FIRST ATTACH");
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

    public void setPlayersCountCurrentPosition(String value) {
        repository.getGame().setPlayersCount(Integer.parseInt(value));
    }

    public void setCurrentAncientOnePosition(String value) {
        repository.setAncientOneId(repository.getAncientOne(value).getId());
    }

    public void setCurrentPreludePosition(String value) {
        repository.getGame().setPreludeID(repository.getPrelude(value).getId());
    }

    public void setSpinnerPosition() {
        int ancientOnePosition = 0;
        if (repository.getGame().getAncientOneID() != -1)
            ancientOnePosition = ancientOneList.indexOf(repository.getAncientOne(repository.getGame().getAncientOneID()).getName());
        getViewState().setSpinnerPosition(ancientOnePosition,
                preludeList.indexOf(repository.getPrelude(repository.getGame().getPreludeID()).getName()),
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
        repository.clearGame();
        super.onDestroy();
    }
}