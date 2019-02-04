package ru.mgusev.eldritchhorror.presentation.presenter.forgotten_endings;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
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
        // -1 - позиция hitn'а = "Древний"
        Timber.d(String.valueOf(position));
        int oldAncientOneId = ancientOneId;
        if (position != -1) {
            ancientOneId = repository.getAncientOne(ancientOneNameList.get(position)).getId();
            getViewState().setResultSwitchVisibility(true);
            getViewState().setAncientOneSpinnerError(null, 0);
        } else {
            ancientOneId = 0;
            getViewState().setResultSwitchVisibility(false);
            getViewState().setAncientOneSpinnerError(repository.getContext().getResources().getString(R.string.forgotten_endings_select_ancient_one_header), 400); //400ms - задержка при показе ошибки, чтобы избежать IllegalArgumentException
            getViewState().hideText();
        }
        getViewState().setAncientOneSpinnerPosition(position + 1); // +1 - поправка на hint
        if (oldAncientOneId != ancientOneId)
            initConditions(ancientOneId, resultValue);
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
        for (String condition : repository.getConditionList(ancientOneId, victory)) {
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
               newRandomIndex = (int)(Math.random() * endingList.size());
            } while (endingList.size() > 1 && randomIndex == newRandomIndex);
            randomIndex = newRandomIndex;
            Ending randomEnding = endingList.get(randomIndex);
            getViewState().showText(randomEnding.getHeader(), randomEnding.getText());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}