package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

public interface GamePhotoView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updatePhotoGallery(List<String> imagesUrlList);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void dispatchTakePictureIntent();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void selectGalleryItem(List<String> list, int position);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openFullScreenPhotoViewer(int position);
}
