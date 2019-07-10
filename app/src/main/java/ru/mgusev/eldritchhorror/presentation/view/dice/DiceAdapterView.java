package ru.mgusev.eldritchhorror.presentation.view.dice;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Dice;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DiceAdapterView extends MvpView {

    void setData(List<Dice> diceList);
}