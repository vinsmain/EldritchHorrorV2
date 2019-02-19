package ru.mgusev.eldritchhorror.presentation.presenter.statistics;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.model.StatisticsInvestigator;
import ru.mgusev.eldritchhorror.presentation.view.statistics.StatisticsView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class StatisticsPresenter extends MvpPresenter<StatisticsView> {

    private static final int DEN = 100;
    private static final int ALL_ANCIENT_ONES_ID = 0;
    private static final int MAX_VALUES_IN_CHART = 12;
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    @Inject
    Repository repository;
    private List<Float> chartValues;
    private List<String> chartLabels;
    private int currentAncientOneId; // 0 - all ancientOnes
    private int investigatorCountSum;
    private Game bestGame;
    private Game lastGame;
    private CompositeDisposable gameListSubscribe;

    public StatisticsPresenter() {
        App.getComponent().inject(this);
        gameListSubscribe = new CompositeDisposable();
        gameListSubscribe.add(repository.getGameListPublish().subscribe(this::updateAllCharts, Timber::d));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().initAncientOneSpinner(getAncientOneNameList());
    }

    public void setCurrentAncientOneId(String spinnerValue) {
        if (spinnerValue.equals(repository.getContext().getResources().getString(R.string.all_results))) {
            currentAncientOneId = ALL_ANCIENT_ONES_ID;
            getViewState().setDefaultBackgroundImage();
        }
        else {
            currentAncientOneId = repository.getAncientOne(spinnerValue).getId();
            getViewState().setBackgroundImage(repository.getAncientOne(currentAncientOneId).getImageResource());
        }
        updateAllCharts(new ArrayList<>());
    }

    private void updateAllCharts(List<Game> list) {
        getViewState().initAncientOneSpinner(getAncientOneNameList());
        try {
            bestGame = repository.getGameList(4, currentAncientOneId).get(0);
            lastGame = repository.getGameList(1, currentAncientOneId).get(0);
        } catch (IndexOutOfBoundsException e) {
            getViewState().finishActivity();
        }
        getViewState().setLastResult(getGameScoreAndDate(lastGame));
        getViewState().setBestResult(getGameScoreAndDate(bestGame));
        getViewState().setVisibilityAncientOneChart(currentAncientOneId == ALL_ANCIENT_ONES_ID);
        getViewState().setVisibilityScoreChart(repository.getVictoryGameCount(currentAncientOneId) != 0);
        getViewState().setVisibilityDefeatReasonChart(repository.getDefeatGameCount(currentAncientOneId) != 0);
        getViewState().setVisibilityInvestigatorChart(repository.getStatisticsInvestigatorList(currentAncientOneId).size() != 0);
        initResultChart();
        initAncientOneChart();
        initScoreChart();
        initDefeatReasonChart();
        initInvestigatorChart();
    }

    private String getGameScoreAndDate(Game game) {
        return (game.getIsWinGame() ? game.getScore() : "") + " (" + new SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(game.getDate()) + ")";
    }

    public void goToDetailsActivity(boolean isLast) {
        if (isLast) repository.setGame(lastGame);
        else repository.setGame(bestGame);
        getViewState().goToDetailsActivity();
    }

    private List<String> getAncientOneNameList() {
        List<String> ancientOneNameList = new ArrayList<>();
        for (AncientOne ancientOne : repository.getAddedAncientOneList()) ancientOneNameList.add(ancientOne.getName());
        ancientOneNameList.add(0, repository.getContext().getResources().getString(R.string.all_results));
        return ancientOneNameList;
    }

    private void initResultChart() {
        initResultValues();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), repository.getGameCount(currentAncientOneId), DEN), chartLabels.get(i)));
        }
        getViewState().setDataToResultChart(entries, repository.getContext().getResources().getString(R.string.win_defeat_stat_description), chartLabels, chartValues, repository.getGameCount(currentAncientOneId));
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

    private void initAncientOneChart() {
        initAncientOneValues();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), repository.getGameCount(ALL_ANCIENT_ONES_ID), DEN), chartLabels.get(i)));
        }
        getViewState().setDataToAncientOneChart(entries, repository.getContext().getResources().getString(R.string.ancientOne), chartLabels, chartValues, repository.getGameCount(ALL_ANCIENT_ONES_ID));
    }

    private void initAncientOneValues() {
        chartValues = new ArrayList<>();
        chartLabels = new ArrayList<>();

        for (AncientOne ancientOne : repository.getAddedAncientOneList()) {
            chartValues.add((float) repository.getAncientOneCountById(ancientOne.getId()));
            chartLabels.add(ancientOne.getName());
        }
    }

    private void initScoreChart() {
        initScoreValues();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), repository.getVictoryGameCount(currentAncientOneId), DEN), chartLabels.get(i)));
        }
        getViewState().setDataToScoreChart(entries, repository.getContext().getResources().getString(R.string.totalScore), chartLabels, chartValues, repository.getVictoryGameCount(currentAncientOneId));
    }

    private void initScoreValues() {
        chartValues = new ArrayList<>();
        chartLabels = new ArrayList<>();
        List<Integer> scoreList = repository.getScoreList(currentAncientOneId);
        int otherCountSum = 0;

        for (int i = 0; i < scoreList.size(); i++) {
            if (i < MAX_VALUES_IN_CHART) {
                chartValues.add((float) repository.getScoreCount(scoreList.get(i), currentAncientOneId));
                chartLabels.add(String.valueOf(scoreList.get(i)));
            } else otherCountSum += repository.getScoreCount(scoreList.get(i), currentAncientOneId);
        }
        if (otherCountSum != 0) {
            chartValues.add((float) otherCountSum);
            chartLabels.add(repository.getContext().getResources().getString(R.string.other_results));
        }
    }

    private void initDefeatReasonChart() {
        initDefeatReasonValues();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), repository.getDefeatGameCount(currentAncientOneId), DEN), chartLabels.get(i)));
        }
        getViewState().setDataToDefeatReasonChart(entries, repository.getContext().getResources().getString(R.string.defeat_table_header), chartLabels, chartValues, repository.getDefeatGameCount(currentAncientOneId));
    }

    private void initDefeatReasonValues() {
        chartValues = new ArrayList<>();
        chartLabels = new ArrayList<>();

        if (repository.getDefeatByEliminationCount(currentAncientOneId) != 0) {
            chartValues.add((float) repository.getDefeatByEliminationCount(currentAncientOneId));
            chartLabels.add(repository.getContext().getResources().getString(R.string.defeat_by_elimination));
        }
        if (repository.getDefeatByMythosDepletionCount(currentAncientOneId) != 0) {
            chartValues.add((float) repository.getDefeatByMythosDepletionCount(currentAncientOneId));
            chartLabels.add(repository.getContext().getResources().getString(R.string.defeat_by_mythos_depletion));
        }
        if (repository.getDefeatByAwakenedAncientOneCount(currentAncientOneId) != 0) {
            chartValues.add((float) repository.getDefeatByAwakenedAncientOneCount(currentAncientOneId));
            chartLabels.add(repository.getContext().getResources().getString(R.string.defeat_by_awakened_ancient_one));
        }
        if (repository.getDefeatBySurrenderCount(currentAncientOneId) != 0) {
            chartValues.add((float) repository.getDefeatBySurrenderCount(currentAncientOneId));
            chartLabels.add(repository.getContext().getResources().getString(R.string.defeat_by_surrender));
        }
        if (repository.getDefeatByRumorCount(currentAncientOneId) != 0) {
            chartValues.add((float) repository.getDefeatByRumorCount(currentAncientOneId));
            chartLabels.add(repository.getContext().getResources().getString(R.string.defeat_by_rumor));
        }
    }

    private void initInvestigatorChart() {
        List<Investigator> investigatorList = new ArrayList<>();
        initInvestigatorValues();
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chartValues.size(); i++) {
            entries.add(new PieEntry(getPercent(chartValues.get(i), investigatorCountSum, DEN), chartLabels.get(i)));
            if (i < 5) {
                Investigator investigator = repository.getInvestigator(chartLabels.get(i));
                investigator.setOccupationEN(new DecimalFormat("#0.0").format(getPercent(chartValues.get(i), investigatorCountSum, DEN)) + "%");
                investigatorList.add(investigator);
            }
        }
        getViewState().setDataToInvestigatorChart(entries, repository.getContext().getResources().getString(R.string.investigators), chartLabels, chartValues, investigatorCountSum);
        getViewState().setInvestigatorList(investigatorList);
    }

    private void initInvestigatorValues() {
        chartValues = new ArrayList<>();
        chartLabels = new ArrayList<>();
        investigatorCountSum = 0;
        int otherCountSum = 0;

        List<StatisticsInvestigator> list = repository.getStatisticsInvestigatorList(currentAncientOneId);
        for (int i = 0; i < list.size(); i++) {
            if (i < MAX_VALUES_IN_CHART) {
                chartValues.add((float) list.get(i).getCount());
                chartLabels.add(repository.getInvestigator(list.get(i).getName()).getName());
            } else otherCountSum += list.get(i).getCount();
            investigatorCountSum += list.get(i).getCount();
        }
        if (otherCountSum != 0) {
            chartValues.add((float) otherCountSum);
            chartLabels.add(repository.getContext().getResources().getString(R.string.other_results));
        }
    }

    private float getPercent(float value, int size, int den) {
        return size == 0 ? 0 : value / size * den;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameListSubscribe.dispose();
    }
}