package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.esafirm.imagepicker.model.Image;

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
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.ImageFile;
import ru.mgusev.eldritchhorror.presentation.view.pager.GamePhotoView;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

@InjectViewState
public class GamePhotoPresenter extends MvpPresenter<GamePhotoView> {

    @Inject
    Repository repository;

    private CompositeDisposable clickPhotoButtonSubscribe;
    private CompositeDisposable updatePhotoGallerySubscribe;
    private CompositeDisposable selectAllPhotoSubscribe;
    private CompositeDisposable deleteSelectImagesSubscribe;
    private List<String> selectedPhotoList;
    private boolean selectMode = false;
    private Uri currentPhotoURI;
    private int currentPosition;

    public GamePhotoPresenter() {
        App.getComponent().inject(this);

        if (repository.getGame() == null && !MainActivity.initialized) repository.setGame(new Game());

        clickPhotoButtonSubscribe = new CompositeDisposable();
        clickPhotoButtonSubscribe.add(repository.getClickPhotoButtonPublish().subscribe(this::addImage, Timber::d));
        updatePhotoGallerySubscribe = new CompositeDisposable();
        updatePhotoGallerySubscribe.add(repository.getUpdatePhotoGalleryPublish().subscribe(this::changeGallery, Timber::d));
        selectAllPhotoSubscribe = new CompositeDisposable();
        selectAllPhotoSubscribe.add(repository.getSelectAllPhotoPublish().subscribe(this::selectAllPhoto, Timber::d));
        deleteSelectImagesSubscribe = new CompositeDisposable();
        deleteSelectImagesSubscribe.add(repository.getDeleteSelectImagesPublish().subscribe(this::deleteSelectImages, Timber::d));
        selectedPhotoList = new ArrayList<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        changeGallery();
        setSelectMode(selectMode);
    }

    public void changeGallery() {
        repository.updatePhotoGalleryOnNext(true);
    }

    private void changeGallery(boolean value) {
        getViewState().updatePhotoGallery(repository.getImages());
    }

    public void addImageFile() {
        ImageFile file = new ImageFile();
        file.setGameId(repository.getGame().getId());
        file.setName(currentPhotoURI.getLastPathSegment());
        file.setLastModified(new Date().getTime());
        repository.addImageFile(file);
        repository.sendFileToStorage(currentPhotoURI, repository.getGame().getId());
    }

    private void addImage(boolean isCamera) {
        if (isCamera) getViewState().dispatchTakePictureIntent();
        else getViewState().openImagePicker();
    }

    private void deleteSelectImages(boolean value) {
        Timber.d("DELETE %s", value);
        if (value) getViewState().showDeleteDialog();
    }

    public File getNewImageFile(){
        return repository.getPhotoFile(getNewImageFileName());
    }

    private String getNewImageFileName() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + "_" + new Date().getTime() + ".jpg";
    }

    public void onSelectImagesFromImagePicker(List<Image> images) {
        for (Image image : images) {
            try {
                File dest = repository.getPhotoFile(getNewImageFileName());
                repository.copyFile(new File(image.getPath()), dest);
                currentPhotoURI = Uri.fromFile(dest);
                addImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (images.size() > 0) repository.updatePhotoGalleryOnNext(true);
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
        changeGallery();
        setSelectMode(false);
    }

    public Uri getCurrentPhotoURI() {
        return currentPhotoURI;
    }

    public void setCurrentPhotoURI(Uri currentPhotoURI) {
        this.currentPhotoURI = currentPhotoURI;
    }

    public void deleteFile(File file) {
        ImageFile imageFile = repository.getImageFile(file.getName());
        repository.removeImageFile(imageFile);
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
        clickPhotoButtonSubscribe.dispose();
        updatePhotoGallerySubscribe.dispose();
        selectAllPhotoSubscribe.dispose();
        deleteSelectImagesSubscribe.dispose();
    }
}