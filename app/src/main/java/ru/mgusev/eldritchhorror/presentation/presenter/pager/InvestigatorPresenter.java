package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorView;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;

@InjectViewState
public class InvestigatorPresenter extends MvpPresenter<InvestigatorView> {

    @Inject
    Repository repository;

    private Investigator investigator;

    public InvestigatorPresenter() {
        App.getComponent().inject(this);

        if (repository.getInvestigator() == null && !MainActivity.initialized) repository.setInvestigator(repository.getInvestigator("Charlie Kane"));

        //Clone current investigator
        investigator = new Investigator(repository.getInvestigator());
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showInvestigatorCard(investigator);
        getViewState().showExpansionIcon(repository.getExpansion(investigator.getExpansionID()).getImageResource());
        getViewState().showSpecializationIcon(repository.getSpecialization(investigator.getSpecialization()).getImageResource());
        getViewState().setMaleOrFemale(investigator.getIsMale());
        getViewState().setListeners();
    }

    public void setInvestigator(boolean isStarting, boolean isReplacement, boolean isDead) {
        investigator.setIsStarting(isStarting);
        investigator.setIsReplacement(isReplacement);
        investigator.setIsDead(isDead);
    }

    @Override
    public void onDestroy() {
        repository.setInvestigator(investigator);
        repository.investigatorOnNext();
        super.onDestroy();
    }
}
