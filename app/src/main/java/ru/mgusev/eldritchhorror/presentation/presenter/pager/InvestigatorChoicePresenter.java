package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class InvestigatorChoicePresenter extends MvpPresenter<InvestigatorChoiceView> {

    private List<Investigator> investigatorList;

    public InvestigatorChoicePresenter() {
        setInvestigatorListFromRepository();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    private void setInvestigatorListFromRepository() {
        investigatorList = Repository.getInstance().getInvestigatorList();
        System.out.println("0000 " + investigatorList);
    }

    public List<Investigator> getInvestigatorList() {
        System.out.println("1111 " + investigatorList);
        return investigatorList;
    }
}
