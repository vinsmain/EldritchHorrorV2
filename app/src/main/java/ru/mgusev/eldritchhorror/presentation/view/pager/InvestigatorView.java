package ru.mgusev.eldritchhorror.presentation.view.pager;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Investigator;

public interface InvestigatorView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showInvestigatorCard(Investigator investigator);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showExpansionIcon(String iconName);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setMaleOrFemale(boolean isMale);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setListeners();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showSpecializationIcon(String iconName);
}
