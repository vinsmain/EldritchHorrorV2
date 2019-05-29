package ru.mgusev.eldritchhorror.presentation.view.dice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

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