package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class InvestigatorPresenter extends MvpPresenter<InvestigatorView> {

    @Inject
    Repository repository;

    public InvestigatorPresenter() {
        App.getComponent().inject(this);
    }
}
