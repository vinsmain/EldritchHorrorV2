package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface PagerView extends MvpView {

    String BACK_DIALOG_TAG = "backDialog";

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAddPhotoButtonIcon(boolean selectedMode);

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
    void setDefeatByRumorIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatBySurrenderIcon();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showScore();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideScore();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void finishActivity();

    @StateStrategyType(SkipStrategy.class)
    void showError();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setEditToolbarHeader();

    @StateStrategyType(SkipStrategy.class)
    void setCurrentPosition(int position);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = BACK_DIALOG_TAG)
    void showBackDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = BACK_DIALOG_TAG)
    void hideBackDialog();

    @StateStrategyType(SingleStateStrategy.class)
    void clearViewState();
}
