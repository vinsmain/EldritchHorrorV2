package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.mgusev.eldritchhorror.model.AncientOne;

public interface ResultGameView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void setResultSwitchChecked(boolean value);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showVictoryTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideVictoryTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDefeatTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideDefeatTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setVictorySwitchText();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatSwitchText();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMysteriesRadioGroup(AncientOne ancientOne);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setDefeatReasonSwitchChecked(boolean v1, boolean v2, boolean v3);
}
