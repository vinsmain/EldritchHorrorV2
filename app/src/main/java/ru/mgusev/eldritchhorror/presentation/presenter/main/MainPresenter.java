package ru.mgusev.eldritchhorror.presentation.presenter.main;

import android.content.Intent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.utils.auth.GoogleAuth;
import ru.mgusev.eldritchhorror.database.oldDB.DatabaseHelperOld;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.view.main.MainView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final int MIN_SORT_MODE = 1;
    private static final int MAX_SORT_MODE = 5;

    @Inject
    Repository repository;
    @Inject
    GoogleAuth googleAuth;
    private List<Game> gameList;
    private CompositeDisposable gameListSubscribe;
    private CompositeDisposable authSubscribe;
    private CompositeDisposable rateSubscribe;

    private Game deletingGame;

    public MainPresenter() {
        App.getComponent().inject(this);
        gameList = new ArrayList<>();
        gameListSubscribe = new CompositeDisposable();
        authSubscribe = new CompositeDisposable();
        rateSubscribe = new CompositeDisposable();
        gameListSubscribe.add(repository.getGameListPublish().subscribe(this::updateGameList, Timber::d));
        authSubscribe.add(repository.getAuthPublish().subscribe(this::authStatusChange, Timber::d));
        rateSubscribe.add(repository.getRatePublish().subscribe(this::showRateDialog, Timber::d));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        importOldUserData();
        gameList = repository.getGameList(0, 0);
        repository.gameListOnNext();
        if (googleAuth.getCurrentUser() != null) signIn();
        else authStatusChange(false);
    }

    private void importOldUserData() {
        new DatabaseHelperOld(repository.getContext()); //import data from old version User DB
        repository.getContext().deleteDatabase("eldritchHorrorDB.db"); //delete old version User DB
        repository.getContext().deleteDatabase("EHLocalDB.db"); //delete old version Static DB
    }

    public void initAuthMenuItem() {
        getViewState().changeAuthItem(googleAuth.getCurrentUser() != null);
    }

    public void setSortModeIcon() {
        getViewState().setSortIcon(repository.getSortMode());
    }

    public void addGame() {
        repository.setGame(new Game());
        getViewState().intentToPager();
    }

    public void setCurrentGame(Game game) {
        repository.setGame(repository.getGame(game.getId()));
    }

    private void updateGameList(List<Game> gameList) {
        this.gameList = new ArrayList<>();
        this.gameList.addAll(gameList);
        showEmptyListMessage();
        setVisibilityStatisticsMenuItem();
        updateStatistics();
        for (Game game : this.gameList) {
            game.setImageFileList(repository.getImageFileListByGameId(game.getId()));
        }
        getViewState().setDataToAdapter(this.gameList);
    }

    private void showEmptyListMessage() {
        if (gameList.isEmpty()) getViewState().showEmptyListMessage();
        else getViewState().hideEmptyListMessage();
    }

    public void setVisibilityStatisticsMenuItem() {
        if (gameList.isEmpty()) getViewState().hideStatisticsMenuItem();
        else getViewState().showStatisticsMenuItem();
    }

    private void updateStatistics() {
        if (repository.getVictoryGameCount(0) == 0) getViewState().setStatistics(repository.getGameCount(0));
        else getViewState().setStatistics(repository.getGameCount(0), repository.getBestScore(), repository.getWorstScore());
    }

    public void showDeleteDialog(Game game) {
        this.deletingGame = game;
        getViewState().showDeleteDialog();
    }

    public void hideDeleteDialog() {
        getViewState().hideDeleteDialog();
    }

    private void showRateDialog(boolean value) {
        getViewState().showRateDialog();
    }

    public void hideRateDialog() {
        getViewState().hideRateDialog();
    }

    public void setRateResult(boolean isRate) {
        repository.setRate(isRate);
    }

    public void deleteGame() {
        repository.deleteGame(deletingGame, true);
        showEmptyListMessage();
        setVisibilityStatisticsMenuItem();
        updateStatistics();
    }

    public void changeSortMode() {
        if (repository.getSortMode() == MAX_SORT_MODE) repository.setSortMode(MIN_SORT_MODE);
        else repository.setSortMode(repository.getSortMode() + 1);
        getViewState().setSortIcon(repository.getSortMode());
        repository.gameListOnNext();
    }


    public void actionAuth() {
        if (googleAuth.getCurrentUser() != null) signOut();
        else signIn();
    }

    private void signIn() {
        getViewState().signIn(googleAuth.getGoogleSignInClient().getSignInIntent());
    }

    private void signOut() {
        googleAuth.signOut();
    }

    public void startAuthTask(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            googleAuth.firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Timber.d(e, "Google sign in failed");
            authStatusChange(false);
            getViewState().showErrorSnackBar();
        }
    }

    private void authStatusChange(boolean isAuth) {
        getViewState().changeAuthItem(isAuth);
        if (isAuth) {
            getViewState().setUserIcon(googleAuth.getCurrentUser().getPhotoUrl());
            getViewState().setUserInfo(googleAuth.getCurrentUser().getDisplayName(), googleAuth.getCurrentUser().getEmail());
        } else {
            getViewState().setUserIcon(null);
            getViewState().setUserInfo(repository.getContext().getResources().getString(R.string.drawer_header_sign_in), repository.getContext().getResources().getString(R.string.drawer_header_sync));
        }
    }

    public void clearGameInRepository() {
        repository.clearGame();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameListSubscribe.dispose();
        authSubscribe.dispose();
        rateSubscribe.dispose();
    }
}