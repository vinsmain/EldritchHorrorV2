package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class InvestigatorPresenter extends MvpPresenter<InvestigatorView> {

    @Inject
    Repository repository;

    private Investigator investigator;

    public InvestigatorPresenter() {
        App.getComponent().inject(this);
        investigator = repository.getInvestigator();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showInvestigatorCard(investigator);
        getViewState().showExpansionIcon(repository.getExpansion(investigator.getExpansionID()).getImageResource());
    }

    public void setInvestigator(boolean isStarting, boolean isReplacement, boolean isDead) {
        investigator.setStarting(isStarting);
        investigator.setReplacement(isReplacement);
        investigator.setDead(isDead);
    }

    @Override
    public void onDestroy() {
        repository.setInvestigator(investigator);
        repository.investigatotOnNext();
        super.onDestroy();
    }
}
