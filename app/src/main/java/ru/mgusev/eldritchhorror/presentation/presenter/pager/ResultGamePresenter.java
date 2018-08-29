package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.view.pager.ResultGameView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class ResultGamePresenter extends MvpPresenter<ResultGameView> {
    @Inject
    Repository repository;
    private CompositeDisposable ancientOneSubscribe;
    private CompositeDisposable resultSwitchSubscribe;

    public ResultGamePresenter() {
        App.getComponent().inject(this);

        ancientOneSubscribe = new CompositeDisposable();
        ancientOneSubscribe.add(repository.getObservableAncientOne().subscribe(ancientOne ->  getViewState().showMysteriesRadioGroup(ancientOne)));

        resultSwitchSubscribe = new CompositeDisposable();
        resultSwitchSubscribe.add(repository.getObservableIsWin().subscribe(this::initResultViews));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setResultSwitchChecked(repository.getGame().getIsWinGame());
        getViewState().setMysteryValue(repository.getGame().getSolvedMysteriesCount());
        setResultValues();
        repository.scoreOnNext(); //устанавливаем счет при первом запуске
        getViewState().setDefeatReasonSwitchChecked(repository.getGame().getIsDefeatByElimination(), repository.getGame().getIsDefeatByMythosDepletion(), repository.getGame().getIsDefeatByAwakenedAncientOne());
    }

    private void setResultValues() {
        Game game = repository.getGame();
        getViewState().setResultValues(game.getGatesCount(), game.getMonstersCount(), game.getCurseCount(), game.getRumorsCount(), game.getCluesCount(), game.getBlessedCount(), game.getDoomCount());
    }

    public void setResultSwitch(boolean value) {
        repository.getGame().setIsWinGame(value);
        repository.isWinOnNext();
    }

    private void initResultViews(boolean value) {
        getViewState().setResultSwitchChecked(value);
        if (value) {
            getViewState().setVictorySwitchText();
            getViewState().showVictoryTable();
            getViewState().hideDefeatTable();
        } else {
            getViewState().setDefeatSwitchText();
            getViewState().showDefeatTable();
            getViewState().hideVictoryTable();
        }
    }

    @Override
    public void onDestroy() {
        ancientOneSubscribe.dispose();
        resultSwitchSubscribe.dispose();
        super.onDestroy();
    }

    public void setResult(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount) {
        repository.getGame().setGatesCount(gatesCount);
        repository.getGame().setMonstersCount(monstersCount);
        repository.getGame().setCurseCount(curseCount);
        repository.getGame().setRumorsCount(rumorsCount);
        repository.getGame().setCluesCount(cluesCount);
        repository.getGame().setBlessedCount(blessedCount);
        repository.getGame().setDoomCount(doomCount);
        repository.getGame().setScore(gatesCount + (int)Math.ceil(monstersCount / 3.0f) + curseCount + rumorsCount * 3 - (int)Math.ceil(cluesCount / 3.0f) - blessedCount - doomCount);
        repository.scoreOnNext();
    }

    public void setDefeatReasons(boolean v1, boolean v2, boolean v3) {
        repository.getGame().setIsDefeatByElimination(v1);
        repository.getGame().setIsDefeatByMythosDepletion(v2);
        repository.getGame().setIsDefeatByAwakenedAncientOne(v3);
        repository.isWinOnNext();
    }

    public void setSolvedMysteriesCount(int mysteryCount) {
        repository.getGame().setSolvedMysteriesCount(mysteryCount);
    }
}