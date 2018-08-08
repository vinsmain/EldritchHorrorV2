package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

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

    public GamePhotoPresenter() {
        App.getComponent().inject(this);
        photoSubscribe = new CompositeDisposable();
        photoSubscribe.add(repository.getPhotoPublish().subscribe(this::makePhoto));
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
        getViewState().dispatchTakePictureIntent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        photoSubscribe.dispose();
    }
}
