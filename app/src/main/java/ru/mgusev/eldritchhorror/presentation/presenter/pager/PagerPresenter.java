package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.os.Environment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.PagerView;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

@InjectViewState
public class PagerPresenter extends MvpPresenter<PagerView> {

    @Inject
    Repository repository;
    private CompositeDisposable ancientOneSubscribe;
    private CompositeDisposable scoreSubscribe;
    private CompositeDisposable isWinSubscribe;
    private CompositeDisposable selectModeSubscribe;
    private boolean selectedMode;
    private int currentPosition = 0;

    public PagerPresenter() {
        App.getComponent().inject(this);

        if (repository.getGame() == null && !MainActivity.initialized) {
            repository.setGame(new Game());
            getViewState().clearViewState();
        }

        ancientOneSubscribe = new CompositeDisposable();
        ancientOneSubscribe.add(repository.getObservableAncientOne().subscribe(ancientOne -> getViewState().setHeadBackground(ancientOne, repository.getExpansion(ancientOne.getExpansionID())), Timber::d));

        scoreSubscribe = new CompositeDisposable();
        scoreSubscribe.add(repository.getObservableScore().subscribe(score -> getViewState().setScore(score), Timber::d));

        isWinSubscribe = new CompositeDisposable();
        isWinSubscribe.add(repository.getObservableIsWin().subscribe(this::setResultIcon, Timber::d));

        selectModeSubscribe = new CompositeDisposable();
        selectModeSubscribe.add(repository.getSelectModePublish().subscribe(this::updateSelectedMode, Timber::d));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        setCurrentPosition(repository.getPagerPosition());
        getViewState().setCurrentPosition(currentPosition);
        if (repository.getGame().getLastModified() != 0) getViewState().setEditToolbarHeader();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    private void setResultIcon(boolean isWin) {
        if (isWin) {
            getViewState().showScore();
            getViewState().setWinIcon();
        } else {
            getViewState().hideScore();
            if (repository.getGame().getIsDefeatByElimination()) getViewState().setDefeatByEliminationIcon();
            else if (repository.getGame().getIsDefeatByMythosDepletion()) getViewState().setDefeatByMythosDepletionIcon();
            else if (repository.getGame().getIsDefeatBySurrender()) getViewState().setDefeatBySurrenderIcon();
            else if (repository.getGame().getIsDefeatByRumor()) getViewState().setDefeatByRumorIcon();
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
            repository.getGame().trimCommentText();
            repository.getGame().clearDefeatRumorID();
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
        if (repository.getGame() != null) {
            Game game = repository.getGame(repository.getGame().getId());
            if (game == null || !game.equals(repository.getGame())) getViewState().showBackDialog();
            else getViewState().finishActivity();
        }
    }

    public void dismissBackDialog() {
        getViewState().hideBackDialog();
    }

    public void clickOnAddPhotoButton(boolean isCamera) {
        repository.clickPhotoButtonOnNext(isCamera);
    }

    public void clickOnDeletePhotoButton() {
        repository.deleteSelectImagesOnNext(true);
    }

    public void deleteFilesIfGameNotCreated() {
        if (repository.getGame(repository.getGame().getId()) == null) {
            repository.removeImageFile(repository.getGame().getId());
            repository.deleteRecursiveFiles(repository.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + repository.getGame().getId()));
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