package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
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
        getViewState().setResultSwitchChecked(repository.getGame().isWinGame());
        repository.scoreOnNext(); //устанавливаем счет при первом запуске
        getViewState().setDefeatReasonSwitchChecked(repository.getGame().isDefeatByElimination(), repository.getGame().isDefeatByMythosDepletion(), repository.getGame().isDefeatByAwakenedAncientOne());
    }

    public void setResultSwitch(boolean value) {
        repository.getGame().setWinGame(value);
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
        repository.setResult(gatesCount, monstersCount, curseCount, rumorsCount, cluesCount, blessedCount, doomCount);
    }

    public void setDefeatReasons(boolean v1, boolean v2, boolean v3) {
        repository.getGame().setDefeatByElimination(v1);
        repository.getGame().setDefeatByMythosDepletion(v2);
        repository.getGame().setDefeatByAwakenedAncientOne(v3);
        repository.isWinOnNext();
    }
}