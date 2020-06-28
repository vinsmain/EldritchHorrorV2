package ru.mgusev.eldritchhorror.presentation.view.forgotten_endings;

import java.util.List;
import java.util.Map;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.AncientOne;

public interface ForgottenEndingsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initAncientOneSpinner(List<String> ancientOneNameList);

    @StateStrategyType(SkipStrategy.class)
    void setItemSelected(int position);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showText(String header, String text);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideText();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setAncientOneSpinnerPosition(int position);

    void setAncientOneImage(AncientOne ancientOne);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setResultSwitchVisibility(boolean visible);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setResultSwitchText(boolean victory);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void createConditionSwitch(Map<String, Boolean> map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void clearConditionsContainer();

    @StateStrategyType(SkipStrategy.class)
    void invalidateView();
}