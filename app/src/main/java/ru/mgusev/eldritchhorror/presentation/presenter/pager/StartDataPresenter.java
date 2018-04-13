package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Calendar;
import java.util.List;

import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class StartDataPresenter extends MvpPresenter<StartDataView> {

    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private Calendar date;
    private List<String> playersCountList;
    private List<String> ancientOneList;
    private List<String> preludeList;

    public StartDataPresenter() {
        date = Calendar.getInstance();
        ancientOneList = Repository.getInstance().getAncientOneNameList();
        preludeList = Repository.getInstance().getPreludeNameList();
        playersCountList = Repository.getInstance().getPlayersCountArray();

        date.setTimeInMillis(Repository.getInstance().getGame().getDate());
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
        Repository.getInstance().getGame().setDate(date.getTimeInMillis());
    }

    public void setDateToField() {
        getViewState().setDateToField(date);
    }

    public void dismissDatePicker() {
        getViewState().dismissDatePicker();
    }

    public void setPlayersCountCurrentPosition(String value) {
        Repository.getInstance().getGame().setPlayersCount(Integer.parseInt(value));
    }

    public void setCurrentAncientOnePosition(String value) {
        Repository.getInstance().setAncientOneId(Repository.getInstance().getAncientOneByName(value).getId());
    }

    public void setCurrentPreludePosition(String value) {
        Repository.getInstance().getGame().setPreludeID(Repository.getInstance().getPreludeByName(value).getId());
    }

    public void setSpinnerPosition() {
        int ancientOnePosition = 0;
        if (Repository.getInstance().getGame().getAncientOneID() != -1)
            ancientOnePosition = ancientOneList.indexOf(Repository.getInstance().getAncientOneNameByID(Repository.getInstance().getGame().getAncientOneID()));
        getViewState().setSpinnerPosition(ancientOnePosition,
                preludeList.indexOf(Repository.getInstance().getPreludeNameByID(Repository.getInstance().getGame().getPreludeID())),
                playersCountList.indexOf(String.valueOf(Repository.getInstance().getGame().getPlayersCount())));
    }

    public void setSwitchValue() {
        Game game = Repository.getInstance().getGame();
        getViewState().setSwitchValue(game.isSimpleMyths(), game.isNormalMyths(), game.isHardMyths(), game.isStartingRumor());
    }

    public void setSwitchValueToGame(boolean easyMythosValue, boolean normalMythosValue, boolean hardMythosValue, boolean startingRumorValue) {
        Repository.getInstance().getGame().setSimpleMyths(easyMythosValue);
        Repository.getInstance().getGame().setNormalMyths(normalMythosValue);
        Repository.getInstance().getGame().setHardMyths(hardMythosValue);
        Repository.getInstance().getGame().setStartingRumor(startingRumorValue);
    }

    @Override
    public void onDestroy() {
        Repository.getInstance().clearGame();
        super.onDestroy();
    }
}