package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.PagerView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class PagerPresenter extends MvpPresenter<PagerView> {

    @Inject
    Repository repository;
    private CompositeDisposable ancientOneSubscribe;
    private CompositeDisposable scoreSubscribe;
    private CompositeDisposable isWinSubscribe;

    public PagerPresenter() {
        App.getComponent().inject(this);
        ancientOneSubscribe = new CompositeDisposable();
        ancientOneSubscribe.add(repository.getObservableAncientOne().subscribe(ancientOne -> getViewState().setHeadBackground(ancientOne, repository.getExpansion(ancientOne.getExpansionID()))));

        scoreSubscribe = new CompositeDisposable();
        scoreSubscribe.add(repository.getObservableScore().subscribe(score -> getViewState().setScore(score)));

        isWinSubscribe = new CompositeDisposable();
        isWinSubscribe.add(repository.getObservableIsWin().subscribe(this::setResultIcon));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setCurrentPosition(repository.getPagerPosition());
    }

    private void setResultIcon(boolean isWin) {
        if (isWin) {
            getViewState().showScore();
            getViewState().setWinIcon();
        } else {
            getViewState().hideScore();
            if (repository.getGame().isDefeatByElimination()) getViewState().setDefeatByEliminationIcon();
            else if (repository.getGame().isDefeatByMythosDepletion()) getViewState().setDefeatByMythosDepletionIcon();
            else getViewState().setDefeatByAwakenedAncientOneIcon();
        }
    }

    public void actionRandom(int position) {
        repository.randomOnNext(position);
    }

    public void actionClear(int position) {
        repository.clearOnNext(position);
    }

    public void actionSave() {
        if (isCorrectActiveInvestigatorsCount()) {
            clearResultValuesIfDefeat();
            repository.insertGame(repository.getGame());
            repository.gameListOnNext();
            getViewState().finishActivity();
        }
        else getViewState().showError();
    }

    private boolean isCorrectActiveInvestigatorsCount() {
        int invCount = 0;
        for (Investigator inv : repository.getGame().getInvList()) {
            if (inv.isStarting()) invCount++;
        }
        return repository.getGame().getPlayersCount() >= invCount;
    }

    private void clearResultValuesIfDefeat() {
        if (!repository.getGame().isWinGame()) {
            repository.getGame().setGatesCount(0);
            repository.getGame().setMonstersCount(0);
            repository.getGame().setCurseCount(0);
            repository.getGame().setRumorsCount(0);
            repository.getGame().setCluesCount(0);
            repository.getGame().setBlessedCount(0);
            repository.getGame().setDoomCount(0);
            repository.getGame().setScore(0);
        }
    }

    public void showBackDialog() {
        getViewState().showBackDialog();
    }

    public void dismissBackDialog() {
        getViewState().hideBackDialog();
    }

    @Override
    public void onDestroy() {
        ancientOneSubscribe.dispose();
        scoreSubscribe.dispose();
        isWinSubscribe.dispose();
        repository.clearGame();
        repository.setPagerPosition(0);
        super.onDestroy();
    }
}