package ru.mgusev.eldritchhorror.presentation.view.dice;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DiceItemView extends MvpView {

    void startAnimation();

    void stopAnimation();

    void updateDice();
}
