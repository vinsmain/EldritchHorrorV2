package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.Calendar;
import java.util.List;

public interface StartDataView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDatePicker(Calendar date);

    void setDateToField(Calendar date);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void dismissDatePicker();

    void initPlayersCountSpinner(List<String> playersCountList);

    void initAncientOneSpinner(List<String> ancientOneList);

    void initPreludeSpinner(List<String> preludeList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setSpinnerPosition(int ancientOnePosition, int preludePosition, int playersCountPosition);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setSwitchValue(boolean easyMythosValue, boolean normalMythosValue, boolean hardMythosValue, boolean startingRumorValue);

}
