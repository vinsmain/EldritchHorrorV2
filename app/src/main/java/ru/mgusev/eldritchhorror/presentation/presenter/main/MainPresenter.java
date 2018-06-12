package ru.mgusev.eldritchhorror.presentation.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.view.main.MainView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    Repository repository;
    private List<Game> gameList;
    private CompositeDisposable gameListSubscribe;
    private int deletingGamePosition;

    public MainPresenter() {
        App.getComponent().inject(this);
        gameListSubscribe = new CompositeDisposable();
        gameListSubscribe.add(repository.getGameListPublish().subscribe(this::updateGameList));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        gameList = repository.getGameList();
        repository.gameListOnNext();
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
        getViewState().setDataToAdapter(this.gameList);
    }

    private void showEmptyListMessage() {
        if (gameList.isEmpty()) getViewState().showEmptyListMessage();
        else getViewState().hideEmptyListMessage();
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameListSubscribe.dispose();
    }
}