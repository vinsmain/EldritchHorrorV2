package ru.mgusev.eldritchhorror.presentation.view.details;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface DetailsView extends MvpView {

    String DELETE_DIALOG_TAG = "deleteDialog";
    String IMAGE_VIEWER_TAG = "fullScreenImageViewer";

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
    void setDefeatByRumorIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatBySurrenderIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatByRumorName(String name);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showScore();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideScore();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setTime(String time);

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

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setVictoryResult(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatReason(boolean isDefeatReasonByElimination, boolean isDefeatReasonByMythosDepletion, boolean isDefeatReasonByAwakenedAncientOne, boolean isDefeatReasonBySurrender, boolean isDefeatReasonByRumor);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideInvestigatorsNotSelected(boolean isHide);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setInvestigatorsList(List<Investigator> list);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = DELETE_DIALOG_TAG)
    void showDeleteDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = DELETE_DIALOG_TAG)
    void hideDeleteDialog();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setPhotoList(List<String> list);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = IMAGE_VIEWER_TAG)
    void openFullScreenPhotoViewer();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = IMAGE_VIEWER_TAG)
    void closeFullScreenPhotoViewer();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showPhotoNoneMessage(boolean isShow);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setComment(String text);
}