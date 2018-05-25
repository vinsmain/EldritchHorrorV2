package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Arrays;
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
    private List<Investigator> activeInvestigatorList;
    private List<Investigator> allInvestigatorList;
    private List<Expansion> expansionList;
    private CompositeDisposable investigatorSubscribe;
    private CompositeDisposable expansionSubscribe;

    public InvestigatorChoicePresenter() {
        App.getComponent().inject(this);
        expansionSubscribe = new CompositeDisposable();
        expansionSubscribe.add(repository.getExpansionPublish().subscribe(this::updateInvListByExpansion));
        activeInvestigatorList = new ArrayList<>();
        investigatorList = repository.getInvestigatorList();
        updateInvListByExpansion(repository.getExpansionList());
        investigatorSubscribe = new CompositeDisposable();
        investigatorSubscribe.add(repository.getInvestigatorPublish().subscribe(this::updateInvListByCurrentInv));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    private void updateInvListByExpansion(List<Expansion> list) {
        if (!list.equals(expansionList)) {
            expansionList = list;
            /*List<Investigator> tempList = new ArrayList<>();
            List<Investigator> allList = repository.getInvestigatorList();

            for (Investigator inv : investigatorList) {
                allList.set(allList.indexOf(inv), inv);
            }

            for (Investigator investigator : allList) {
                if (investigator.isStarting() || investigator.isReplacement() || isActiveInv(investigator))
                    tempList.add(investigator);
            }
            investigatorList = tempList;
            getViewState().updateAllItems(investigatorList);*/

            updateAllInv();
            investigatorList.clear();
            investigatorList.addAll(activeInvestigatorList);
            investigatorList.addAll(allInvestigatorList);
            getViewState().updateAllItems(investigatorList);
        }
    }

    private boolean isActiveInv(Investigator inv) {
        for (Expansion expansion : expansionList) {
            if (expansion.getId() == inv.getExpansionID()) return expansion.isEnable();
        }
        return false;
    }

    /*private void sortList() {
        List<Investigator> sortedList = new ArrayList<>();
        for (Investigator investigator)
    }*/

    private void updateInvListByCurrentInv(Investigator currentInvestigator) {
        //investigatorList.set(investigatorList.indexOf(currentInvestigator), currentInvestigator);
        //updateInvListByExpansion(expansionList);

        /*int position = 0;
        for (int i = 0; i < investigatorList.size(); i++) {
            if (investigatorList.get(i).getId() == currentInvestigator.getId()) {
                position = i;
                break;
            }
        }
        if (currentInvestigator.isStarting() || currentInvestigator.isReplacement() || isActiveInv(currentInvestigator)) {
            investigatorList.add(0, currentInvestigator);
            investigatorList.remove (position + 1);
            getViewState().updateItem(position, investigatorList);
        } else {
            investigatorList.remove(position);
            getViewState().removeItem(position, investigatorList);
        }*/

        int position = 0;
        if (currentInvestigator.isStarting() || currentInvestigator.isReplacement()) {
            if (activeInvestigatorList.contains(currentInvestigator)) {
                position = activeInvestigatorList.indexOf(currentInvestigator);
                activeInvestigatorList.remove(currentInvestigator);
                activeInvestigatorList.add(0, currentInvestigator);
            } else {
                position = allInvestigatorList.indexOf(currentInvestigator) + activeInvestigatorList.size();
                activeInvestigatorList.add(0, currentInvestigator);
            }
            getViewState().removeItem(position, investigatorList);

            //getViewState().updateItem(position, investigatorList);
        } else {
            if (activeInvestigatorList.contains(currentInvestigator)) {
                position = activeInvestigatorList.indexOf(currentInvestigator);
                activeInvestigatorList.remove(currentInvestigator);
                
                activeInvestigatorList.add(0, currentInvestigator);
            } else {
                position = allInvestigatorList.indexOf(currentInvestigator) + activeInvestigatorList.size();
                activeInvestigatorList.add(0, currentInvestigator);
            }
            getViewState().removeItem(position, investigatorList);




            investigatorList.remove(position);
            getViewState().removeItem(position, investigatorList);
        }
    }



    private void updateAllInv() {
        allInvestigatorList = repository.getInvestigatorList();
        for (int i = 0; i < activeInvestigatorList.size(); i++) {
            int index = allInvestigatorList.indexOf(activeInvestigatorList.get(i));
            allInvestigatorList.remove(index);
        }
        allInvestigatorList = deleteDisableInv(allInvestigatorList);
    }

    private List<Investigator> deleteDisableInv(List<Investigator> list) {
        List<Investigator> tempList = new ArrayList<>();
        for (Investigator investigator : list) {
            if (isActiveInv(investigator)) tempList.add(investigator);
        }
        return tempList;
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