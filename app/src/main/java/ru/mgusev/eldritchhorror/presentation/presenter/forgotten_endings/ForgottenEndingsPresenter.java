package ru.mgusev.eldritchhorror.presentation.presenter.forgotten_endings;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
            getViewState().setAncientOneSpinnerError("Выберите Древнего", 400); //500ms - задержка при показе ошибки, чтобы избежать IllegalArgumentException
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
    }

    public void onConditionCheckedChanged(String text, boolean checked) {
        getViewState().clearConditionsContainer(); //TODO Проверить необходимость этой строки
        conditionMap.put(text, checked);
        getViewState().createConditionSwitch(conditionMap); //TODO Проверить необходимость этой строки
    }

    public void onReadTextBtnClick() {
        List<String> conditionList = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : conditionMap.entrySet()) {
            if (entry.getValue())
                conditionList.add(entry.getKey());
        }
        endingList = repository.getEndingList(ancientOneId, resultValue, conditionList);
        for (Ending ending : endingList) {
            Timber.d(ending.getHeader());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}