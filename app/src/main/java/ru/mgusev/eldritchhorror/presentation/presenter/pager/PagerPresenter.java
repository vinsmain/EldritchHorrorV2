package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
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
        ancientOneSubscribe.add(repository.getObservableAncientOne().subscribe(ancientOne -> getViewState().setHeadBackground(ancientOne, getRepository().getExpansion(ancientOne.getExpansionID()))));

        scoreSubscribe = new CompositeDisposable();
        scoreSubscribe.add(repository.getObservableScore().subscribe(score -> getViewState().setScore(score)));

        isWinSubscribe = new CompositeDisposable();
        isWinSubscribe.add(repository.getObservableIsWin().subscribe(this::setResultIcon));
    }

    public Repository getRepository() {
        return repository;
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

    @Override
    public void onDestroy() {
        ancientOneSubscribe.dispose();
        scoreSubscribe.dispose();
        isWinSubscribe.dispose();
        super.onDestroy();
    }
}