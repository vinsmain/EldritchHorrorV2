package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class InvestigatorChoicePresenter extends MvpPresenter<InvestigatorChoiceView> {

    @Inject
    Repository repository;
    private List<Investigator> investigatorList;
    private List<Expansion> expansionList;
    private CompositeDisposable investigatorSubscribe;
    private CompositeDisposable expansionSubscribe;

    public InvestigatorChoicePresenter() {
        App.getComponent().inject(this);
        expansionSubscribe = new CompositeDisposable();
        expansionSubscribe.add(repository.getExpansionPublish().subscribe(this::updateInvListByExpansion));
        investigatorList = repository.getInvestigatorList();
        updateInvListByExpansion(repository.getExpansionList());
        refreshInvList(-1);
        investigatorSubscribe = new CompositeDisposable();
        investigatorSubscribe.add(repository.getInvestigatorPublish().subscribe(this::updateInvListByCurrentInv));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    private void updateInvListByExpansion(List<Expansion> list) {
        expansionList = list;
        List<Investigator> tempList = new ArrayList<>();
        List<Investigator> allList = repository.getInvestigatorList();

        for (Investigator inv : investigatorList) {
            allList.set(allList.indexOf(inv), inv);
        }

        for (Investigator investigator : allList) {
            if (investigator.isStarting() || investigator.isReplacement() || isActiveInv(investigator)) tempList.add(investigator);
        }
        investigatorList = tempList;
        refreshInvList(-1);
    }

    private boolean isActiveInv(Investigator inv) {
        for (Expansion expansion : expansionList) {
            if (expansion.getId() == inv.getExpansionID()) return expansion.isEnable();
        }
        return false;
    }

    private void updateInvListByCurrentInv(Investigator currentInvestigator) {
        //investigatorList.set(investigatorList.indexOf(currentInvestigator), currentInvestigator);
        //updateInvListByExpansion(expansionList);

        int position = 0;
        for (int i = 0; i < investigatorList.size(); i++) {
            if (investigatorList.get(i).getId() == currentInvestigator.getId()) {
                position = i;
                break;
            }
        }
        if (currentInvestigator.isStarting() || currentInvestigator.isReplacement() || isActiveInv(currentInvestigator)) {
            investigatorList.set(position, currentInvestigator);
            refreshInvList(-1);
        } else {
            investigatorList.remove(position);
            refreshInvList(position);
        }

        //refreshInvList(-1);
    }

    private void refreshInvList(int i) {
        getViewState().showItems(investigatorList, repository.getExpansionList(), i);

    }

    public void itemClick(int position) {
        repository.setInvestigator(investigatorList.get(position));
        getViewState().showInvestigatorActivity();
    }

    @Override
    public void onDestroy() {
        investigatorSubscribe.dispose();
        expansionSubscribe.dispose();
        super.onDestroy();
    }
}