package ru.mgusev.eldritchhorror.presentation.view.pager;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Specialization;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SpecializationChoiceView extends MvpView {

    void initSpecializationList(List<Specialization> list);
}
