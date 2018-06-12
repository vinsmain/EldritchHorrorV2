package ru.mgusev.eldritchhorror.presentation.view.main;

import android.content.Context;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface MainView extends MvpView {

    String DELETE_DIALOG_TAG = "deleteDialog";

    @StateStrategyType(SkipStrategy.class)
    void intentToPager();

    @StateStrategyType(SkipStrategy.class)
    void intentToDetails();

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = DELETE_DIALOG_TAG)
    void showDeleteDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = DELETE_DIALOG_TAG)
    void hideDeleteDialog();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDataToAdapter(List<Game> gameList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showEmptyListMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideEmptyListMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void deleteGame(int position, List<Game> gameList);
}
