package ru.mgusev.eldritchhorror.presentation.view.forgotten_endings;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;
import java.util.Map;

public interface ForgottenEndingsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initAncientOneSpinner(List<String> ancientOneNameList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAncientOneSpinnerError(String text, int time);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showText(String header, String text);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideText();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAncientOneSpinnerPosition(int position);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setResultSwitchVisibility(boolean visible);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setResultSwitchText(boolean victory);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void createConditionSwitch(Map<String, Boolean> map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void clearConditionsContainer();
}