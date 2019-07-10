package ru.mgusev.eldritchhorror.presentation.view.forgotten_endings;

import java.util.List;
import java.util.Map;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

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