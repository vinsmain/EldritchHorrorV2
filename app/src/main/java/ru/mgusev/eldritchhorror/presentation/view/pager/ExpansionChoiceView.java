package ru.mgusev.eldritchhorror.presentation.view.pager;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Expansion;

public interface ExpansionChoiceView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initExpansionList(List<Expansion> list);
}
