package ru.mgusev.eldritchhorror.presentation.presenter.details;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.view.details.DetailsView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    @Inject
    Repository repository;
    private Game game;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public DetailsPresenter() {
        App.getComponent().inject(this);
        game = repository.getGame();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initGameData();
    }

    private void initGameData() {
        initHeader();
        initStartData();
        initMysteriesCount();
        if (game.isWinGame()) initVictory();
        else initDefeat();
    }

    private void initHeader() {
        getViewState().setHeadBackground(repository.getAncientOne(game.getAncientOneID()), repository.getExpansion(repository.getAncientOne(game.getAncientOneID()).getExpansionID()));
    }

    private void initStartData() {
        getViewState().setInitialConditions(formatter.format(game.getDate()), repository.getAncientOne(game.getAncientOneID()).getName(),
                repository.getPrelude(game.getPreludeID()).getName(), String.valueOf(game.getPlayersCount()));
        getViewState().setAdditionalRules(game.isSimpleMyths(), game.isNormalMyths(), game.isHardMyths(), game.isStartingRumor());
    }

    private void initMysteriesCount() {
        getViewState().setMysteriesCount(String.valueOf(game.getSolvedMysteriesCount()));
    }

    private void initVictory() {
        getViewState().setScore(game.getScore());
        getViewState().showScore();
        getViewState().setVictoryIcon();
        getViewState().showVictoryCard();
        getViewState().setVictoryResult(game.getGatesCount(), game.getMonstersCount(), game.getCurseCount(),
                game.getRumorsCount(), game.getCluesCount(), game.getBlessedCount(), game.getDoomCount());
    }

    private void initDefeat() {
        getViewState().hideScore();
        if (game.isDefeatByElimination()) getViewState().setDefeatByEliminationIcon();
        else if (game.isDefeatByMythosDepletion()) getViewState().setDefeatByMythosDepletionIcon();
        else getViewState().setDefeatByAwakenedAncientOneIcon();
        getViewState().showDefeatCard();
        getViewState().setDefeatReason(game.isDefeatByElimination(), game.isDefeatByMythosDepletion(), game.isDefeatByAwakenedAncientOne());
    }
}