package ru.mgusev.eldritchhorror.presentation.view.dice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DiceItemView extends MvpView {

    void startAnimation();

    void stopAnimation();

    void updateDice();
}
