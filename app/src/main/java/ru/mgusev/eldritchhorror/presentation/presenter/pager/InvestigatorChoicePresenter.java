package ru.mgusev.eldritchhorror.presentation.presenter.pager;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.view.pager.InvestigatorChoiceView;
import ru.mgusev.eldritchhorror.repository.Repository;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

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
    private CompositeDisposable randomSubscribe;
    private CompositeDisposable clearSubscribe;

    public InvestigatorChoicePresenter() {
        App.getComponent().inject(this);

        if (repository.getGame() == null && !MainActivity.initialized) repository.setGame(new Game());

        expansionSubscribe = new CompositeDisposable();
        expansionSubscribe.add(repository.getExpansionPublish().subscribe(this::updateInvListByExpansion, Timber::d));
        activeInvestigatorList = new ArrayList<>();
        activeInvestigatorList.addAll(repository.getGame().getInvList());
        investigatorList = new ArrayList<>();
        updateInvListByExpansion(repository.getExpansionList());
        investigatorSubscribe = new CompositeDisposable();
        investigatorSubscribe.add(repository.getInvestigatorPublish().subscribe(this::updateInvListByCurrentInv, Timber::d));
        randomSubscribe = new CompositeDisposable();
        randomSubscribe.add(repository.getRandomPublish().subscribe(this::setRandomValues, Timber::d));
        clearSubscribe = new CompositeDisposable();
        clearSubscribe.add(repository.getClearPublish().subscribe(this::clearValues, Timber::d));
    }

    private void updateInvListByExpansion(List<Expansion> list) {
        if (!list.equals(expansionList)) {
            expansionList = list;

            updateAllInvestigatorList();
            updateInvestigatorList();
            getViewState().updateAllItems(investigatorList);
        }
    }

    private boolean isActiveInvestigator(Investigator inv) {
        for (Expansion expansion : expansionList) {
            if (expansion.getId() == inv.getExpansionID()) return expansion.isEnable();
        }
        return false;
    }

    private int getCountStartingInv() {
        int countStartingInv = 0;
        for (Investigator investigator : activeInvestigatorList) {
            if (investigator.getIsStarting()) countStartingInv++;
        }
        return countStartingInv;
    }

    private void updateInvListByCurrentInv(Investigator currentInvestigator) {
        //Проверка на превышение числи стартовых сыщиков
        if (currentInvestigator.getIsStarting() && getCountStartingInv() == repository.getGame().getPlayersCount()
                && (!activeInvestigatorList.contains(currentInvestigator) || !activeInvestigatorList.get(activeInvestigatorList.indexOf(currentInvestigator)).getIsStarting())) {
            getViewState().showError();
        }
        //Сыщик стал активным
        else if (currentInvestigator.getIsStarting() || currentInvestigator.getIsReplacement()) {
            //Был активным ранее
            if (activeInvestigatorList.contains(currentInvestigator)) {
                int position = activeInvestigatorList.indexOf(currentInvestigator);
                activeInvestigatorList.set(position, currentInvestigator);
                updateInvestigatorList();
                getViewState().updateItem(position, investigatorList);
            //Не был активным ранее
            } else {
                int positionOld = allInvestigatorList.indexOf(currentInvestigator);
                allInvestigatorList.remove(currentInvestigator);
                activeInvestigatorList.add(0, currentInvestigator);
                updateInvestigatorList();
                getViewState().moveItem(positionOld + activeInvestigatorList.size() - 1, 0, investigatorList);
            }
        //Сыщик стал неактивным
        } else {
            //Был активным ранее и принадлежит к выбранным дополнениям
            if (activeInvestigatorList.contains(currentInvestigator) && isActiveInvestigator(currentInvestigator)) {
                int positionOld = activeInvestigatorList.indexOf(currentInvestigator);
                activeInvestigatorList.remove(currentInvestigator);
                updateAllInvestigatorList();
                updateInvestigatorList();
                int position = investigatorList.indexOf(currentInvestigator);
                getViewState().moveItem(positionOld, position, investigatorList);
            //Был активным ранее и не принадлежит к выбранным дополнениям
            } else if (activeInvestigatorList.contains(currentInvestigator) && !isActiveInvestigator(currentInvestigator)) {
                int position = activeInvestigatorList.indexOf(currentInvestigator);
                activeInvestigatorList.remove(currentInvestigator);
                updateAllInvestigatorList();
                updateInvestigatorList();
                getViewState().removeItem(position, investigatorList);
            }
        }
    }

    private void setRandomValues(int position) {
        if (position == 1) {
            choiceRandomInvestigators();
        }
    }

    public void clearInvestigatorList() {
        activeInvestigatorList = new ArrayList<>();
        updateAllInvestigatorList();
        updateInvestigatorList();
        getViewState().updateAllItems(investigatorList);
    }

    private void choiceRandomInvestigators() {
        clearInvestigatorList();

        Random random = new Random();
        for (int i = 0; i < repository.getGame().getPlayersCount(); i++) {
            Investigator inv;
            do {
                int index = random.nextInt(allInvestigatorList.size());
                inv = allInvestigatorList.get(index);
            } while (isRepeatSpecialization(inv) && activeInvestigatorList.size() < repository.getSpecializationList().size());
            inv.setIsStarting(true);
            updateInvListByCurrentInv(inv);
        }
    }

    private boolean isRepeatSpecialization(Investigator inv) {
        for (Investigator investigator : activeInvestigatorList) {
            if (investigator.getSpecialization() == inv.getSpecialization()) return true;
        }
        return !repository.getSpecialization(inv.getSpecialization()).isEnable() && activeInvestigatorList.size() < repository.getEnabledSpecializationCount();
    }

    private void updateInvestigatorList() {
        repository.getGame().setInvList(activeInvestigatorList);
        investigatorList = new ArrayList<>();
        investigatorList.addAll(activeInvestigatorList);
        investigatorList.addAll(allInvestigatorList);
        repository.getGame().setInvList(activeInvestigatorList);
    }

    private void updateAllInvestigatorList() {
        allInvestigatorList = repository.getInvestigatorList();
        for (int i = 0; i < activeInvestigatorList.size(); i++) {
            allInvestigatorList.remove(activeInvestigatorList.get(i));
        }

        List<Investigator> tempList = new ArrayList<>();
        for (Investigator investigator : allInvestigatorList) {
            if (isActiveInvestigator(investigator)) tempList.add(investigator);
        }
        allInvestigatorList = tempList;
    }

    private void clearValues(int position) {
        if (position == 1) {
            getViewState().showClearInvListDialog();
        }
    }

    public void dismissDialog() {
        getViewState().hideClearInvListDialog();
    }

    public void itemClick(int position) {
        repository.setInvestigator(investigatorList.get(position));
        getViewState().showInvestigatorActivity();
    }


    @Override
    public void onDestroy() {
        investigatorSubscribe.dispose();
        expansionSubscribe.dispose();
        randomSubscribe.dispose();
        clearSubscribe.dispose();
        super.onDestroy();
    }
}