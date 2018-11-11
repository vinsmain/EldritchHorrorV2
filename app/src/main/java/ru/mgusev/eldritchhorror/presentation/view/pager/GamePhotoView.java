package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;
import java.util.List;

import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface GamePhotoView extends MvpView {

    String IMAGE_VIEWER_TAG = "fullScreenImageViewer";
    String DELETE_DIALOG_TAG = "deleteDialog";

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updatePhotoGallery(List<String> imagesUrlList);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = DELETE_DIALOG_TAG)
    void showDeleteDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = DELETE_DIALOG_TAG)
    void hideDeleteDialog();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void dispatchTakePictureIntent();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void selectGalleryItem(List<String> list, int position);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = IMAGE_VIEWER_TAG)
    void openFullScreenPhotoViewer();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = IMAGE_VIEWER_TAG)
    void closeFullScreenPhotoViewer();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openImagePicker();
}
