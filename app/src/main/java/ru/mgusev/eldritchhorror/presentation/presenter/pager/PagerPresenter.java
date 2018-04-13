package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mgusev.eldritchhorror.presentation.view.pager.PagerView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class PagerPresenter extends MvpPresenter<PagerView> {

    public PagerPresenter() {
        Repository.getInstance().getObservableAncientOne().subscribe(ancientOne ->  getViewState().setHeadBackground(ancientOne));
    }
}