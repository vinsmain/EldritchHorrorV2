package ru.mgusev.eldritchhorror.presentation.presenter.statistics;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.AncientOne;
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
    private int currentAncientOneId; // 0 - all ancientOnes

    public StatisticsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initAncientOneSpinner(getAncientOneNameList());
    }

    public void setCurrentAncientOneId(String spinnerValue) {
        if (spinnerValue.equals(repository.getContext().getResources().getString(R.string.all_results))) {
            currentAncientOneId = 0;
            getViewState().setDefaultBackgroundImage();
        }
        else {
            currentAncientOneId = repository.getAncientOne(spinnerValue).getId();
            getViewState().setBackgroundImage(repository.getAncientOne(currentAncientOneId).getImageResource());
        }
        initResultChart();
        initAncientOneChart();
    }

    private List<String> getAncientOneNameList() {
        List<String> ancientOneNameList = new ArrayList<>();
        for (AncientOne ancientOne : repository.getAddedAncientOneList()) ancientOneNameList.add(ancientOne.getName());
        ancientOneNameList.add(0, repository.getContext().getResources().getString(R.string.all_results));
        return ancientOneNameList;
    }

    public void initResultChart() {
        initResultValues();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), repository.getGameCount(), DEN), chartLabels.get(i)));
        }
        getViewState().setDataToResultChart(entries, repository.getContext().getResources().getString(R.string.win_defeat_stat_description), chartLabels, chartValues, repository.getGameCount());
    }

    private void initResultValues() {
        chartValues = new ArrayList<>();
        chartLabels = new ArrayList<>();

        if (repository.getVictoryGameCount(currentAncientOneId) != 0) {
            chartValues.add((float) repository.getVictoryGameCount(currentAncientOneId));
            chartLabels.add(repository.getContext().getResources().getString(R.string.win_stat_label));
        }
        if (repository.getDefeatGameCount(currentAncientOneId) != 0) {
            chartValues.add((float) repository.getDefeatGameCount(currentAncientOneId));
            chartLabels.add(repository.getContext().getResources().getString(R.string.defeat_stat_label));
        }
    }




    public void initAncientOneChart() {
        initAncientOneValues();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), repository.getGameCount(), DEN), chartLabels.get(i)));
        }
        getViewState().setDataToAncientOneChart(entries, repository.getContext().getResources().getString(R.string.ancientOne), chartLabels, chartValues, repository.getGameCount());
    }

    private void initAncientOneValues() {
        chartValues = new ArrayList<>();
        chartLabels = new ArrayList<>();

        for (AncientOne ancientOne : repository.getAddedAncientOneList()) {
            chartValues.add((float) repository.getAncientOneCountById(ancientOne.getId()));
            chartLabels.add(ancientOne.getName());
        }
    }

    private float getPercent(float value, int size, int den) {
        return size == 0 ? 0 : value / size * den;
    }
}