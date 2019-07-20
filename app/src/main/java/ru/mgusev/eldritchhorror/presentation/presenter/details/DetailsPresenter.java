package ru.mgusev.eldritchhorror.presentation.presenter.details;

import android.net.Uri;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.mgusev.eldritchhorror.BuildConfig;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.details.DetailsView;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.utils.FormattedTime;
import timber.log.Timber;

@InjectViewState
public class DetailsPresenter extends MvpPresenter<DetailsView> {

    @Inject
    Repository repository;
    private Game game;
    private CompositeDisposable gameListSubscribe;
    private CompositeDisposable photoSubscribe;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private int currentPosition;
    private List<String> selectedPhotoList;
    private boolean selectMode = false;

    public DetailsPresenter() {
        App.getComponent().inject(this);

        gameListSubscribe = new CompositeDisposable();
        gameListSubscribe.add(repository.getGameListPublish().subscribe(this::initGameData, Timber::d));
        photoSubscribe = new CompositeDisposable();
        photoSubscribe.add(repository.getUpdatePhotoGalleryPublish().subscribe(this::initPhotoList, Timber::d));
        selectedPhotoList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        initGameData(new ArrayList<>());
    }

    private void initGameData(List<Game> list) {
        game = repository.getGame();
        if (game != null) {
            initHeader();
            initStartData();
            initInvestigatorList();
            initTime();
            initMysteriesCount();
            if (game.getIsWinGame()) initVictory();
            else initDefeat();
            initComment();
            initPhotoList(true);
        }
    }

    private void initHeader() {
        getViewState().setHeadBackground(repository.getAncientOne(game.getAncientOneID()), repository.getExpansion(repository.getAncientOne(game.getAncientOneID()).getExpansionID()));
    }

    private void initStartData() {
        getViewState().setInitialConditions(formatter.format(game.getDate()), repository.getAncientOne(game.getAncientOneID()).getName(),
                repository.getPrelude(game.getPreludeID()).getName(), String.valueOf(game.getPlayersCount()));
        getViewState().setAdditionalRules(game.getIsSimpleMyths(), game.getIsNormalMyths(), game.getIsHardMyths(), game.getIsStartingRumor());
    }

    private void initInvestigatorList() {
        List<Investigator> investigatorList = game.getInvList();
        getViewState().hideInvestigatorsNotSelected(investigatorList.isEmpty());
        getViewState().setInvestigatorsList(investigatorList);
    }

    private void initPhotoList(boolean value) {
        List<String> photoList = repository.getImages();
        getViewState().showPhotoNoneMessage(photoList.isEmpty());
        getViewState().setPhotoList(photoList);
    }

    private void initTime() {
        getViewState().setTime(FormattedTime.getTime(repository.getContext(), game.getTime()));
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
        if (game.getIsDefeatByElimination()) getViewState().setDefeatByEliminationIcon();
        else if (game.getIsDefeatByMythosDepletion()) getViewState().setDefeatByMythosDepletionIcon();
        else if (game.getIsDefeatBySurrender()) getViewState().setDefeatBySurrenderIcon();
        else if (game.getIsDefeatByRumor()) {
            getViewState().setDefeatByRumorIcon();
            getViewState().setDefeatByRumorName(repository.getRumor(repository.getGame().getDefeatRumorID()).getName());
        }
        else getViewState().setDefeatByAwakenedAncientOneIcon();
        getViewState().showDefeatCard();
        getViewState().setDefeatReason(game.getIsDefeatByElimination(), game.getIsDefeatByMythosDepletion(), game.getIsDefeatByAwakenedAncientOne(), game.getIsDefeatBySurrender(), game.getIsDefeatByRumor());
    }

    private void initComment() {
        String comment = game.getComment();
        getViewState().setComment(comment == null || comment.trim().equals("") ? repository.getContext().getResources().getString(R.string.comment_none) : comment);
    }

    public void showDeleteDialog() {
        getViewState().showDeleteDialog();
    }

    public void hideDeleteDialog() {
        getViewState().hideDeleteDialog();
    }

    public void deleteGame() {
        repository.deleteGame(game, true);
        gameListSubscribe.dispose();
        repository.gameListOnNext();
    }

    public void setCurrentPagerPosition(int position) {
        repository.setPagerPosition(position);
    }

    public void setGameToRepository() {
        repository.setGame(game);
    }

    public void openFullScreenPhotoViewer() {
        getViewState().openFullScreenPhotoViewer();
    }

    public void closeFullScreenPhotoViewer() {
        getViewState().closeFullScreenPhotoViewer();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void shareImage() {
        repository.shareImage(getImageUriList());
    }

    private List<Uri> getImageUriList() {
        List<Uri> uris = new ArrayList<>();
        if (selectedPhotoList.isEmpty()) {
            uris.add(FileProvider.getUriForFile(repository.getContext(), BuildConfig.APPLICATION_ID + ".provider", new File(repository.getImages().get(currentPosition).substring(5))));
        } else {
            for (String path : selectedPhotoList) {
                uris.add(FileProvider.getUriForFile(repository.getContext(), BuildConfig.APPLICATION_ID + ".provider", new File(path.substring(5))));
            }
        }
        return uris;
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
    }

    public void selectGalleryItem(String selectedItem, int position) {
        if (selectedPhotoList.contains(selectedItem)) {
            selectedPhotoList.remove(selectedItem);
            if (selectedPhotoList.isEmpty()) setSelectMode(false);
        }
        else selectedPhotoList.add(selectedItem);
        getViewState().selectGalleryItem(selectedPhotoList, position);
    }

    public void selectAllPhoto(boolean isSelectAll) {
        List<String> imagesUriList = repository.getImages();
        if (!imagesUriList.isEmpty()) {
            selectedPhotoList.clear();
            if (isSelectAll) {
                selectedPhotoList.addAll(imagesUriList);
                setSelectMode(true);
            } else setSelectMode(false);
            getViewState().selectGalleryItem(selectedPhotoList, 0);
            getViewState().updatePhotoGallery(imagesUriList);
        }
    }

    @Override
    public void onDestroy() {
        gameListSubscribe.dispose();
        photoSubscribe.dispose();
        super.onDestroy();
    }
}