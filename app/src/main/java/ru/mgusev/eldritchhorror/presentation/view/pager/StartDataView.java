package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.Calendar;
import java.util.List;

import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface StartDataView extends MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = "date")
    void showDatePicker(Calendar date);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDateToField(Calendar date);

    @StateStrategyType(value = DismissDialogStrategy.class, tag = "date")
    void dismissDatePicker();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initPlayersCountSpinner(List<String> playersCountList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initAncientOneSpinner(List<String> ancientOneList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initPreludeSpinner(List<String> preludeList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setSpinnerPosition(int ancientOnePosition, int preludePosition, int playersCountPosition);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setSwitchValue(boolean easyMythosValue, boolean normalMythosValue, boolean hardMythosValue, boolean startingRumorValue);

}
