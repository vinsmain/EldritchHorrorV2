package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Expansion;

public interface ExpansionChoiceView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initExpansionList(List<Expansion> list);
}
