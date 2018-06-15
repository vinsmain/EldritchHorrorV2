package ru.mgusev.eldritchhorror.presentation.presenter.statistics;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.statistics.StatisticsView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class StatisticsPresenter extends MvpPresenter<StatisticsView> {

    private static final int DEN = 100;
    private static final int MAX_VALUES_IN_CHART = 12;
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    @Inject
    Repository repository;
    private List<Float> chartValues;
    private List<String> chartLabels;

    public StatisticsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        System.out.println("222");
        getViewState().initFragments();
    }

    public void initResultChart() {
        //winDefeatChart = findViewById(R.id.win_defeat_pie_chart);
        //winDefeatChart.setOnChartValueSelectedListener(winDefeatChart);

        initWinDefeatValues();

        List<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), repository.getGameCount(), DEN), chartLabels.get(i)));
        }
        System.out.println("333");
        getViewState().setDataToResultChart(entries, repository.getContext().getResources().getString(R.string.win_defeat_stat_description), chartLabels, chartValues, repository.getGameCount());
    }

    private void initWinDefeatValues() {
        chartValues = new ArrayList<>();
        chartLabels = new ArrayList<>();

        if (repository.getVictoryGameCount() != 0) {
            chartValues.add((float) repository.getVictoryGameCount());
            chartLabels.add(repository.getContext().getResources().getString(R.string.win_stat_label));
        }
        if (repository.getDefeatGameCount() != 0) {
            chartValues.add((float) repository.getDefeatGameCount());
            chartLabels.add(repository.getContext().getResources().getString(R.string.defeat_stat_label));
        }
    }

    private float getPercent(float value, int size, int den) {
        return size == 0 ? 0 : value / size * den;
    }
}