package ru.mgusev.eldritchhorror.presentation.view.details;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;

public interface DetailsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setHeadBackground(AncientOne ancientOne, Expansion expansion);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setScore(int score);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setVictoryIcon();

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

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setMysteriesCount(String mysteriesCount);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showVictoryCard();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDefeatCard();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setInitialConditions(String date, String ancientOne, String prelude, String playersCount);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAdditionalRules(boolean isSimpleMyths, boolean isNormalMyths, boolean isHardMyths, boolean isStartingRumor);

    void setVictoryResult(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount);

    void setDefeatReason(boolean isDefeatReasonByElimination, boolean isDefeatReasonByMythosDepletion, boolean isDefeatReasonByAwakenedAncientOne);
}
