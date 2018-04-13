package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ResultGameView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setResultSwitchText(boolean value);

    @StateStrategyType(SkipStrategy.class)
    void setResultSwitchChecked(boolean value);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showResultTable(boolean value);
}
