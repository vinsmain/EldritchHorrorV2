package ru.mgusev.eldritchhorror.presentation.view.random_card;

import android.net.Uri;

import com.arellomobile.mvp.MvpView;

public interface RandomCardView extends MvpView {
    void loadImage(Uri source);

    void setTitle(String text);

    void setCategory(String text);

    void setType(String text);
}
