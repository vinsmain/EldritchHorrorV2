package ru.mgusev.eldritchhorror.presentation.view.dice;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Dice;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DiceView extends MvpView {

    void setVisibilityScreenLightButtons();

    void setScreenLightFlags(boolean isScreenLightOn);

    void setDiceCountValue(String value);

    void updateDiceList(List<Dice> diceList);

    void startAnimation();

    void stopAnimation();
}
