package ru.mgusev.eldritchhorror.presentation.presenter.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.view.main.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    public MainPresenter() {

    }

    public void addGame() {
        getViewState().showMessage(R.string.donate);
    }


}