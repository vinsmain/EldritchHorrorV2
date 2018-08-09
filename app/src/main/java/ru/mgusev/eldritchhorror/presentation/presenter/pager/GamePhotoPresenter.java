package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.pager.GamePhotoView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class GamePhotoPresenter extends MvpPresenter<GamePhotoView> {

    @Inject
    Repository repository;

    private CompositeDisposable photoSubscribe;
    private List<String> selectedPhotoList;
    private boolean selectMode = false;
    private Uri currentPhotoURI;

    public GamePhotoPresenter() {
        App.getComponent().inject(this);
        photoSubscribe = new CompositeDisposable();
        photoSubscribe.add(repository.getPhotoPublish().subscribe(this::makePhoto));
        selectedPhotoList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updateGallery();
    }

    public void updateGallery() {
        getViewState().updatePhotoGallery(repository.getImages());
    }

    private void makePhoto(boolean value) {
        if (selectedPhotoList.isEmpty()) getViewState().dispatchTakePictureIntent();
        else deleteFiles();
    }

    public void selectGalleryItem(String selectedItem, int position) {
        if (selectedPhotoList.contains(selectedItem)) {
            selectedPhotoList.remove(selectedItem);
            if (selectedPhotoList.isEmpty()) setSelectMode(false);
        }
        else selectedPhotoList.add(selectedItem);
        getViewState().selectGalleryItem(selectedPhotoList, position);
    }

    public boolean isSelectMode() {
        return selectMode;
    }

    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
        repository.selectModeOnNext(this.selectMode);
    }

    public void openFullScreenPhotoViewer(int position) {
        getViewState().openFullScreenPhotoViewer(position);
    }

    private void deleteFiles() {
        for (String uri : selectedPhotoList) {
            File file = new File(Uri.parse(uri).getPath());
            if (file.exists()) file.delete();
        }
        selectedPhotoList.clear();
        updateGallery();
        setSelectMode(false);
    }

    public long getGameId() {
        return repository.getGame().getId();
    }

    public Uri getCurrentPhotoURI() {
        return currentPhotoURI;
    }

    public void setCurrentPhotoURI(Uri currentPhotoURI) {
        this.currentPhotoURI = currentPhotoURI;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        photoSubscribe.dispose();
    }
}