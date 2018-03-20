package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.Calendar;

public interface StartDataView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDatePicker(Calendar date);

    void setDateToField(Calendar date);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void dismissDatePicker();

}
