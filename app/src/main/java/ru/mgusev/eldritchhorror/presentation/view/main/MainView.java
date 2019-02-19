package ru.mgusev.eldritchhorror.presentation.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

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
    void deleteGame(int position, List<Game> gameList);

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