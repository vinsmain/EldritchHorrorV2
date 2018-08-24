package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.ImageFile;
import ru.mgusev.eldritchhorror.presentation.view.pager.GamePhotoView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class GamePhotoPresenter extends MvpPresenter<GamePhotoView> {

    @Inject
    Repository repository;

    private CompositeDisposable photoSubscribe;
    private CompositeDisposable selectAllPhotoSubscribe;
    private List<String> selectedPhotoList;
    private boolean selectMode = false;
    private Uri currentPhotoURI;
    private int currentPosition;

    public GamePhotoPresenter() {
        App.getComponent().inject(this);
        photoSubscribe = new CompositeDisposable();
        photoSubscribe.add(repository.getPhotoPublish().subscribe(this::makePhoto));
        selectAllPhotoSubscribe = new CompositeDisposable();
        selectAllPhotoSubscribe.add(repository.getSelectAllPhotoPublish().subscribe(this::selectAllPhoto));
        selectedPhotoList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updateGallery();
        //repository.fireStoreSubscribe();
    }

    public void updateGallery() {
        getViewState().updatePhotoGallery(repository.getImages());
        repository.photoOnNext(false);
    }

    public void addImageFile() {
        ImageFile file = new ImageFile();
        file.setGameId(repository.getGame().getId());
        file.setName(currentPhotoURI.getLastPathSegment());
        repository.addImageFile(file);
        repository.sendFileToStorage(currentPhotoURI);
    }

    private void makePhoto(boolean value) {
        if (value) {
            if (selectedPhotoList.isEmpty()) {
                String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + "_";
                getViewState().dispatchTakePictureIntent(repository.getPhotoFile(imageFileName));
            }
            else getViewState().showDeleteDialog();
        }
    }

    public void dismissDeleteDialog() {
        getViewState().hideDeleteDialog();
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

    private void selectAllPhoto(boolean isSelectAll) {
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

    public void openFullScreenPhotoViewer() {
        getViewState().openFullScreenPhotoViewer();
    }

    public void closeFullScreenPhotoViewer() {
        getViewState().closeFullScreenPhotoViewer();
    }

    public void deleteSelectedFiles() {
        for (String uri : selectedPhotoList) {
            deleteFile(new File(Uri.parse(uri).getPath()));
        }
        selectedPhotoList.clear();
        updateGallery();
        setSelectMode(false);
    }

    public Uri getCurrentPhotoURI() {
        return currentPhotoURI;
    }

    public void setCurrentPhotoURI(Uri currentPhotoURI) {
        this.currentPhotoURI = currentPhotoURI;
    }

    public void deleteFile(File file) {
        System.out.println(Uri.fromFile(file).getLastPathSegment());
        ImageFile imageFile = repository.getImageFile(file.getName());
        repository.removeImageFile(imageFile);
        repository.removeFileFromFirebase(imageFile);
        repository.deleteRecursiveFiles(file);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        photoSubscribe.dispose();
        selectAllPhotoSubscribe.dispose();
    }
}