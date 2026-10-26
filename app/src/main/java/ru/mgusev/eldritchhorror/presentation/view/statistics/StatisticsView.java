package ru.mgusev.eldritchhorror.presentation.view.statistics;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.model.statistics.StatisticsChart;

public interface StatisticsView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initAncientOneSpinner(List<String> ancientOneList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setItemSelected(int position);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setBackgroundImage(String imageResource);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefaultBackgroundImage();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setInvestigatorList(List<Investigator> list);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setBestResult(String value);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setLastResult(String value);

    @StateStrategyType(SkipStrategy.class)
    void goToDetailsActivity();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setCharts(List<StatisticsChart> charts);

    @StateStrategyType(SkipStrategy.class)
    void invalidateView();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void finishActivity();
}
