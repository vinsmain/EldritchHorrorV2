package ru.mgusev.eldritchhorror.presentation.presenter.statistics;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mgusev.eldritchhorror.presentation.view.statistics.StatisticsView;

@InjectViewState
public class StatisticsPresenter extends MvpPresenter<StatisticsView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initFragments();
    }
}