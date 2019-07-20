package ru.mgusev.eldritchhorror.presentation.view.dice;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DiceView extends MvpView {

    void setVisibilityScreenLightButtons();

    void setScreenLightFlags(boolean isScreenLightOn);

    void setVisibilityAnimationModeButtons();

    void setVisibilitySuccessModeButtons();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setInitialValueForSeekBar(int value);

    void setDiceCountValue(String value);

    void setSuccessCount(String count);
}