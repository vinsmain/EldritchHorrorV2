package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.database.HelperFactory;
import ru.mgusev.eldritchhorror.presentation.App;
import ru.mgusev.eldritchhorror.presentation.view.pager.StartDataView;

@InjectViewState
public class StartDataPresenter extends MvpPresenter<StartDataView> {

    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private Calendar date;
    private String[] playersCountArray;
    private List<String> ancientOneList;
    private List<String> preludeList;
    private int currentPlayersCountPosition;
    private int ancientOneCurrentPosition;
    private int preludeCurrentPosition;

    public StartDataPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        date = Calendar.getInstance();
        setDateToField();

        ancientOneList = new ArrayList<>();
        preludeList = new ArrayList<>();
        playersCountArray = App.getContext().getResources().getStringArray(R.array.playersCountArray);
        try {
            ancientOneList.addAll(HelperFactory.getStaticHelper().getAncientOneDAO().getAncientOneNameList());
            preludeList.addAll(HelperFactory.getStaticHelper().getPreludeDAO().getPreludeNameList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getViewState().initAncientOneSpinner(ancientOneList);
        getViewState().initPreludeSpinner(preludeList);
        getViewState().initPlayersCountSpinner(playersCountArray);
        System.out.println("FIRST ATTACH");
    }

    public void showDatePicker() {
        getViewState().showDatePicker(date);
    }

    public void setDate(int year, int month, int day) {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setDateToField() {
        getViewState().setDateToField(date);
    }

    public void dismissDatePicker() {
        getViewState().dismissDatePicker();
    }

    public void setCurrentPlayersCountPosition(int position) {
        currentPlayersCountPosition = position;
    }

    public void setCurrentAncientOnePosition(int position) {
        ancientOneCurrentPosition = position;
    }

    public void setCurrentPreludePosition(int position) {
        preludeCurrentPosition = position;
    }

    public void setSpinnerPosition() {
        getViewState().setSpinnerPosition(ancientOneCurrentPosition, preludeCurrentPosition, currentPlayersCountPosition);
    }
}
