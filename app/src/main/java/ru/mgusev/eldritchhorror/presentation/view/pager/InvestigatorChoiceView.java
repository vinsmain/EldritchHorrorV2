package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.model.Investigator;

public interface InvestigatorChoiceView extends MvpView {

    void updateAllItems(List<Investigator> investigatorList);

    void updateItem(int position, List<Investigator> investigatorList);

    void removeItem(int position, List<Investigator> investigatorList);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showInvestigatorActivity();
}
