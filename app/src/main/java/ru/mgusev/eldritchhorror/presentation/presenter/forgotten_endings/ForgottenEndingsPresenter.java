package ru.mgusev.eldritchhorror.presentation.presenter.forgotten_endings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.mgusev.eldritchhorror.di.App;
import ru.mgusev.eldritchhorror.model.Ending;
import ru.mgusev.eldritchhorror.presentation.view.forgotten_endings.ForgottenEndingsView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class ForgottenEndingsPresenter extends MvpPresenter<ForgottenEndingsView> {

    @Inject
    Repository repository;

    private List<String> ancientOneNameList;
    private List<Ending> endingList;
    private Map<String, Boolean> conditionMap;
    private int ancientOneId;
    private boolean resultValue = true;
    private int randomIndex = -1;

    public ForgottenEndingsPresenter() {
        App.getComponent().inject(this);
        conditionMap = new HashMap<>();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initAncientOneSpinner(getAncientOneNameList());
    }

    private List<String> getAncientOneNameList() {
        ancientOneNameList = new ArrayList<>();
        for (int ancientOneId : repository.getAncientOneIdFromForgottenEndings()) {
            ancientOneNameList.add(repository.getAncientOne(ancientOneId).getName());
        }
        Collections.sort(ancientOneNameList);
        return ancientOneNameList;
    }

    public void onAncientOneSelected(int position) {
        Timber.d(String.valueOf(position));
        int oldAncientOneId = ancientOneId;
        ancientOneId = repository.getAncientOne(ancientOneNameList.get(position)).getId();
        getViewState().setResultSwitchVisibility(true);
        getViewState().setAncientOneSpinnerPosition(position);
        getViewState().setAncientOneImage(repository.getAncientOne(ancientOneId));
        if (oldAncientOneId != ancientOneId)
            initConditions(ancientOneId, resultValue);
    }

    public void setSpinnerPosition() {
        if (ancientOneId == 0)
            getViewState().setItemSelected(0);
        else
            getViewState().setItemSelected(ancientOneNameList.indexOf(repository.getAncientOne(ancientOneId).getName()));
    }

    public void onResultCheckedChanged(boolean checked) {
        if (resultValue != checked) {
            resultValue = checked;
            Timber.d("%s", resultValue);
            getViewState().setResultSwitchText(checked);
            initConditions(ancientOneId, resultValue);
        }
    }

    private void initConditions(int ancientOneId, boolean victory) {
        getViewState().clearConditionsContainer();
        conditionMap.clear();
        for (String condition : repository.getCardList(ancientOneId, victory)) {
            conditionMap.put(condition, false);
        }
        getViewState().createConditionSwitch(conditionMap);
        getViewState().hideText();
    }

    public void onConditionCheckedChanged(String text, boolean checked) {
        if (checked) {
            getViewState().clearConditionsContainer();
            conditionMap.clear();
            conditionMap.put(text, true);
            getViewState().createConditionSwitch(conditionMap);
            getViewState().hideText();
        } else initConditions(ancientOneId, resultValue);

        getViewState().invalidateView();
    }

    public void onReadTextBtnClick() {
        List<String> conditionList = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : conditionMap.entrySet()) {
            if (entry.getValue())
                conditionList.add(entry.getKey());
        }
        endingList = repository.getEndingList(ancientOneId, resultValue, conditionList);

        if (!endingList.isEmpty()) {
            int newRandomIndex;
            do {
                newRandomIndex = (int) (Math.random() * endingList.size());
            } while (endingList.size() > 1 && randomIndex == newRandomIndex);
            randomIndex = newRandomIndex;
            Ending randomEnding = endingList.get(randomIndex);
            getViewState().showText(randomEnding.getHeader(), randomEnding.getText());
        }

        getViewState().invalidateView();
    }
}