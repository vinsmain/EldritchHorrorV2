package ru.mgusev.eldritchhorror.presentation.view.main;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface MainView extends MvpView {

    String DELETE_DIALOG_TAG = "deleteDialog";
    String RATE_DIALOG_TAG = "deleteDialog";

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showStatisticsMenuItem();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideStatisticsMenuItem();

    @StateStrategyType(SkipStrategy.class)
    void intentToPager();

    @StateStrategyType(SkipStrategy.class)
    void intentToDetails();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void changeAuthItem(boolean signedIn);

    @StateStrategyType(SkipStrategy.class)
    void setSortIcon(int sortMode);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = DELETE_DIALOG_TAG)
    void showDeleteDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = DELETE_DIALOG_TAG)
    void hideDeleteDialog();

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = RATE_DIALOG_TAG)
    void showRateDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = RATE_DIALOG_TAG)
    void hideRateDialog();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDataToAdapter(List<Game> gameList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showEmptyListMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideEmptyListMessage();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void intentToGooglePlay();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setStatistics(int gameCount, int bestScore, int worstScore);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setStatistics(int gameCount);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void signIn(Intent signInIntent);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setUserIcon(Uri iconUri);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setUserInfo(String name, String email);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showErrorSnackBar();
}