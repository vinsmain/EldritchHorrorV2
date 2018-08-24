package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.os.Environment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
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
    private CompositeDisposable selectModeSubscribe;
    private boolean selectedMode;

    public PagerPresenter() {
        App.getComponent().inject(this);
        ancientOneSubscribe = new CompositeDisposable();
        ancientOneSubscribe.add(repository.getObservableAncientOne().subscribe(ancientOne -> getViewState().setHeadBackground(ancientOne, repository.getExpansion(ancientOne.getExpansionID()))));

        scoreSubscribe = new CompositeDisposable();
        scoreSubscribe.add(repository.getObservableScore().subscribe(score -> getViewState().setScore(score)));

        isWinSubscribe = new CompositeDisposable();
        isWinSubscribe.add(repository.getObservableIsWin().subscribe(this::setResultIcon));

        selectModeSubscribe = new CompositeDisposable();
        selectModeSubscribe.add(repository.getSelectModePublish().subscribe(this::updateSelectedMode));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setCurrentPosition(repository.getPagerPosition());
        if (repository.getGame().getLastModified() != 0) getViewState().setEditToolbarHeader();
    }

    private void setResultIcon(boolean isWin) {
        if (isWin) {
            getViewState().showScore();
            getViewState().setWinIcon();
        } else {
            getViewState().hideScore();
            if (repository.getGame().getIsDefeatByElimination()) getViewState().setDefeatByEliminationIcon();
            else if (repository.getGame().getIsDefeatByMythosDepletion()) getViewState().setDefeatByMythosDepletionIcon();
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
            repository.getGame().clearResultValuesIfDefeat();
            repository.insertGame(repository.getGame());
            repository.gameListOnNext();
            repository.rateOnNext();
            getViewState().finishActivity();
        }
        else getViewState().showError();
    }

    private boolean isCorrectActiveInvestigatorsCount() {
        int invCount = 0;
        for (Investigator inv : repository.getGame().getInvList()) {
            if (inv.getIsStarting()) invCount++;
        }
        return repository.getGame().getPlayersCount() >= invCount;
    }

    public void showBackDialog() {
        Game game = repository.getGame(repository.getGame().getId());
        if (game == null || !game.equals(repository.getGame())) getViewState().showBackDialog();
        else getViewState().finishActivity();
    }

    public void dismissBackDialog() {
        getViewState().hideBackDialog();
    }

    public void clickOnAddPhotoButton() {
        repository.photoOnNext(true);
    }

    public void deleteFilesIfGameNotCreated() {
        if (repository.getGame(repository.getGame().getId()) == null) {
            repository.deleteRecursiveFiles(repository.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + repository.getGame().getId()));
            repository.removeImageFile(repository.getGame().getId());
        }
    }

    public void clickSelectPhoto(boolean isSelectAll) {
        repository.selectAllPhotoOnNext(isSelectAll);
    }

    private void updateSelectedMode(boolean selectedMode) {
        this.selectedMode = selectedMode;
        getViewState().setAddPhotoButtonIcon(selectedMode);
    }

    public boolean isSelectedMode() {
        return selectedMode;
    }

    @Override
    public void onDestroy() {
        ancientOneSubscribe.dispose();
        scoreSubscribe.dispose();
        isWinSubscribe.dispose();
        selectModeSubscribe.dispose();
        repository.clearGame();
        repository.setPagerPosition(0);
        super.onDestroy();
    }
}