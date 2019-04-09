package ru.mgusev.eldritchhorror.presentation.presenter.random_card;

import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardView;

@InjectViewState
public class RandomCardPresenter extends MvpPresenter<RandomCardView> {

    public RandomCardPresenter() {
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().loadImage(Uri.parse("https://mgusevstudio.ru/images/files/eh_images/en/condition/injury/back_injury.png"));
//        getViewState().setTitle("Амнезия");
//        getViewState().setCategory("Состояние");
//        getViewState().setType("БЕЗУМИЕ");
    }
}
