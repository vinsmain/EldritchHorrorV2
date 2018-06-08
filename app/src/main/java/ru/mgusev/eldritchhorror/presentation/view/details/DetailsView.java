package ru.mgusev.eldritchhorror.presentation.view.details;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Game;

public interface DetailsView extends MvpView {

    /*@StateStrategyType(SkipStrategy.class)
    void intentToPager();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDataToAdapter(List<Game> gameList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showEmptyListMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideEmptyListMessage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void deleteGame(int position, List<Game> gameList);*/
}
