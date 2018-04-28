package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;

public interface PagerView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setHeadBackground(AncientOne ancientOne, Expansion expansion);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setScore(int score);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setWinIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatByEliminationIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatByMythosDepletionIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatByAwakenedAncientOneIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showScore();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideScore();
}
