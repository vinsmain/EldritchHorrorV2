package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface GamePhotoView extends MvpView {
    void updatePhotoGallery(List<String> imagesUrlList);

    void dispatchTakePictureIntent();
}
