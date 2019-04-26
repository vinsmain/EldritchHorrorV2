package ru.mgusev.eldritchhorror.presentation.view.random_card;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.CardType;

public interface RandomCardCategoryView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setCategoryList(List<CardType> list);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startRandomCardActivity();
}