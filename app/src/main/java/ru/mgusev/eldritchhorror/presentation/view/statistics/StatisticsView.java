package ru.mgusev.eldritchhorror.presentation.view.statistics;

import android.widget.TextView;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public interface StatisticsView extends MvpView {

    void initAncientOneSpinner(List<String> ancientOneList);

    void setBackgroundImage(String imageResource);

    void setDefaultBackgroundImage();

    @StateStrategyType(SkipStrategy.class)
    void initFragments();

    void initResultChartFragment();

    void setDataToResultChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum);

    void initAncientOneFragment();

    void setDataToAncientOneChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum);

    void initScoreChartFragment();

    void initDefeatReasonChartFragment();

    void initInvestigatorChartFragment();
}
