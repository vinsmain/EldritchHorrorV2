package ru.mgusev.eldritchhorror.presentation.presenter.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.auth.GoogleAuth;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.view.main.MainView;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.support.CircularTransformation;

import static android.content.ContentValues.TAG;

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
    private CompositeDisposable userIconSubscribe;
    private String tempIconUrl;

    private int deletingGamePosition;

    public MainPresenter() {
        App.getComponent().inject(this);
        gameListSubscribe = new CompositeDisposable();
        userIconSubscribe = new CompositeDisposable();
        gameListSubscribe.add(repository.getGameListPublish().subscribe(this::updateGameList));
        userIconSubscribe.add(repository.getUserIconPublish().subscribe(this::updateUserIcon));
        tempIconUrl = googleAuth.getDefaultIconUrl();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        gameList = repository.getGameList(0, 0);
        repository.gameListOnNext();
        if (googleAuth.getCurrentUser() != null) auth();
    }

    public void startUpdateUserIcon() {
        updateUserIcon(tempIconUrl);
    }

    private void updateUserIcon(String iconUrl) {
        tempIconUrl = iconUrl;
        if (!iconUrl.equals(googleAuth.getDefaultIconUrl())) {
            final Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    Log.d("DEBUG", "onBitmapLoaded");
                    BitmapDrawable mBitmapDrawable = new BitmapDrawable(repository.getContext().getResources(), bitmap);
                    //                                mBitmapDrawable.setBounds(0,0,24,24);
                    // setting icon of Menu Item or Navigation View's Menu Item
                    getViewState().setUserIcon(mBitmapDrawable);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.d("DEBUG", "onBitmapFailed");
                    getViewState().setUserIcon(repository.getContext().getResources().getDrawable(R.drawable.google_signed_in));
                }

                @Override
                public void onPrepareLoad(Drawable drawable) {
                    Log.d("DEBUG", "onPrepareLoad");
                    getViewState().setUserIcon(repository.getContext().getResources().getDrawable(R.drawable.google_signed_in));
                }
            };

            Picasso.get()
                    .load(iconUrl)
                    .transform(new CircularTransformation())
                    .into(target);
        } else getViewState().setUserIcon(repository.getContext().getResources().getDrawable(R.drawable.google_icon));
    }

    public void setSortModeIcon() {
        getViewState().setSortIcon(repository.getSortMode());
    }

    public void addGame() {
        getViewState().intentToPager();
    }

    public void setCurrentGame(int position) {
        repository.setGame(gameList.get(position));
    }

    private void updateGameList(List<Game> gameList) {
        this.gameList = new ArrayList<>();
        this.gameList.addAll(gameList);
        showEmptyListMessage();
        setVisibilityStatisticsMenuItem();
        updateStatistics();
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

    public void showDeleteDialog(int deletingGamePosition) {
        this.deletingGamePosition = deletingGamePosition;
        getViewState().showDeleteDialog();
    }

    public void hideDeleteDialog() {
        getViewState().hideDeleteDialog();
    }

    public void deleteGame() {
        repository.deleteGame(gameList.get(deletingGamePosition));
        gameList.remove(deletingGamePosition);
        getViewState().deleteGame(deletingGamePosition, gameList);
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
        System.out.println("123 " + googleAuth.getCurrentUser());
        if (googleAuth.getCurrentUser() != null) getViewState().showSignOutMenu();
        else auth();
    }

    public void auth() {
        getViewState().signIn(googleAuth.getmGoogleSignInClient().getSignInIntent());
    }

    public void signOut() {
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
            Log.w(TAG, "Google sign in failed", e);
            tempIconUrl = googleAuth.getDefaultIconUrl();
            getViewState().setUserIcon(repository.getContext().getResources().getDrawable(R.drawable.google_icon));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameListSubscribe.dispose();
        userIconSubscribe.dispose();
    }
}