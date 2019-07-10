package ru.mgusev.eldritchhorror.presentation.view.random_card;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.CardType;

public interface RandomCardCategoryView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void setVisibilityScreenLightButtons();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setCategoryList(List<CardType> list);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startRandomCardActivity();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setScreenLightFlags(boolean isScreenLightOn);
}