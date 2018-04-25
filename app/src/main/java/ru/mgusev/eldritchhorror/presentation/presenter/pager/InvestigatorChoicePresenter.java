package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class InvestigatorChoicePresenter extends MvpPresenter<InvestigatorChoiceView> {

    @Inject
    Repository repository;
    private List<Investigator> investigatorList;

    public InvestigatorChoicePresenter() {
        App.getComponent().inject(this);
        investigatorList = repository.getInvestigatorList();
        setInvestigatorListFromRepository();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    private void setInvestigatorListFromRepository() {
        getViewState().showItems(investigatorList, repository.getExpansionList());

    }

    public void itemClick(int position) {
        repository.setCurrentInvestigator(investigatorList.get(position));
        getViewState().showInvestigatorActivity();
    }
}
