package ru.mgusev.eldritchhorror.presentation.view.dice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Dice;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DiceAdapterView extends MvpView {

    void setData(List<Dice> diceList);
}