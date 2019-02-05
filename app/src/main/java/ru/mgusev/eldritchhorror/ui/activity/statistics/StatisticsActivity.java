package ru.mgusev.eldritchhorror.ui.activity.statistics;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.StatisticsAdapter;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.presentation.presenter.statistics.StatisticsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.statistics.StatisticsView;
import ru.mgusev.eldritchhorror.ui.activity.details.DetailsActivity;
import ru.mgusev.eldritchhorror.ui.fragment.statistics.ChartFragment;

public class StatisticsActivity extends MvpAppCompatActivity implements StatisticsView {

    @BindView(R.id.statistics_toolbar) Toolbar toolbarStatistics;
    @BindView(R.id.statistics_chart_container) LinearLayout chartContainer;
    @BindView(R.id.statistics_ancient_one_spinner) Spinner ancientOneSpinner;
    @BindView(R.id.statistics_popular_investigator_container) LinearLayout investigatorLL;
    @BindView(R.id.statistics_popular_investigators_recycler_view) RecyclerView investigatorsRV;
    @BindView(R.id.statistics_background) ImageView background;
    @BindView(R.id.statistics_last_game) TextView lastGameTV;
    @BindView(R.id.statistics_best_game) TextView bestGameTV;
    @BindView(R.id.loader) LinearLayout loader;

    @InjectPresenter
    StatisticsPresenter statisticsPresenter;
    private ArrayAdapter<String> ancientOneAdapter;
    private int columnsCount = 3;
    private StatisticsAdapter statisticsAdapter;
    private ChartFragment resultChartFragment;
    private ChartFragment ancientOneChartFragment;
    private ChartFragment scoreChartFragment;
    private ChartFragment defeatReasonChartFragment;
    private ChartFragment investigatorChartFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        initToolbar();

        ancientOneAdapter = new ArrayAdapter<>(this, R.layout.spinner);
        ancientOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ancientOneSpinner.setAdapter(ancientOneAdapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnsCount = 5;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnsCount);
        investigatorsRV.setLayoutManager(gridLayoutManager);
        investigatorsRV.setHasFixedSize(true);

        statisticsAdapter = new StatisticsAdapter();
        investigatorsRV.setAdapter(statisticsAdapter);

        initFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoader();
    }

    private void initToolbar() {
        setSupportActionBar(toolbarStatistics);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.statistics_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarStatistics.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void initAncientOneSpinner(List<String> ancientOneNameList) {
        ancientOneAdapter.clear();
        ancientOneAdapter.addAll(ancientOneNameList);
        ancientOneAdapter.notifyDataSetChanged();
    }

    @OnItemSelected(R.id.statistics_ancient_one_spinner)
    public void onItemSelected() {
        statisticsPresenter.setCurrentAncientOneId((String) ancientOneSpinner.getSelectedItem());
    }

    @Override
    public void setBackgroundImage(String imageResource) {
        background.setImageResource(getResources().getIdentifier(imageResource, "drawable", getPackageName()));
    }

    @Override
    public void setDefaultBackgroundImage() {
        background.setImageResource(R.drawable.eh_main);
    }

    @Override
    public void setInvestigatorList(List<Investigator> list) {
        List<Investigator> investigatorList = new ArrayList<>();
        for (int i = 0; i < list.size() && i < columnsCount; i++) investigatorList.add(list.get(i));
        statisticsAdapter.updateAllInvCards(investigatorList);
    }

    @Override
    public void initFragments() {
        removeAllFragments();
        initResultChartFragment();
        initAncientOneFragment();
        initScoreChartFragment();
        initDefeatReasonChartFragment();
        initInvestigatorChartFragment();
    }

    private void removeAllFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    private void showLoader() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loader.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void setBestResult(String value) {
        bestGameTV.setText(value);
    }

    @Override
    public void setLastResult(String value) {
        lastGameTV.setText(value);
    }

    @OnClick({R.id.statistics_last_game_row, R.id.statistics_best_game_row, R.id.statistics_filter_card})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.statistics_last_game_row :
                showLoader();
                statisticsPresenter.goToDetailsActivity(true);
                break;
            case R.id.statistics_best_game_row :
                showLoader();
                statisticsPresenter.goToDetailsActivity(false);
                break;
            case R.id.statistics_filter_card :
                ancientOneSpinner.performClick();
                break;
        }
    }

    @Override
    public void goToDetailsActivity() {
        Intent detailsIntent = new Intent(this, DetailsActivity.class);
        startActivity(detailsIntent);
    }

    public void initResultChartFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        resultChartFragment = new ChartFragment();
        fragmentTransaction.add(R.id.statistics_chart_container, resultChartFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setDataToResultChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        resultChartFragment.setData(entries, description, labels, values, sum);
    }

    public void initAncientOneFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ancientOneChartFragment = new ChartFragment();
        fragmentTransaction.add(R.id.statistics_chart_container, ancientOneChartFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setDataToAncientOneChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        ancientOneChartFragment.setData(entries, description, labels, values, sum);
    }

    @Override
    public void setVisibilityAncientOneChart(boolean isVisible) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isVisible) fragmentTransaction.show(ancientOneChartFragment);
        else fragmentTransaction.hide(ancientOneChartFragment);
        fragmentTransaction.commit();
    }

    public void initScoreChartFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        scoreChartFragment = new ChartFragment();
        fragmentTransaction.add(R.id.statistics_chart_container, scoreChartFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setDataToScoreChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        scoreChartFragment.setData(entries, description, labels, values, sum);
    }

    @Override
    public void setVisibilityScoreChart(boolean isVisible) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isVisible) fragmentTransaction.show(scoreChartFragment);
        else fragmentTransaction.hide(scoreChartFragment);
        fragmentTransaction.commit();
    }


    public void initDefeatReasonChartFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        defeatReasonChartFragment = new ChartFragment();
        fragmentTransaction.add(R.id.statistics_chart_container, defeatReasonChartFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setDataToDefeatReasonChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        defeatReasonChartFragment.setData(entries, description, labels, values, sum);
    }

    @Override
    public void setVisibilityDefeatReasonChart(boolean isVisible) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isVisible) fragmentTransaction.show(defeatReasonChartFragment);
        else fragmentTransaction.hide(defeatReasonChartFragment);
        fragmentTransaction.commit();
    }


    public void initInvestigatorChartFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        investigatorChartFragment = new ChartFragment();
        fragmentTransaction.add(R.id.statistics_chart_container, investigatorChartFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setDataToInvestigatorChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        investigatorChartFragment.setData(entries, description, labels, values, sum);
    }

    @Override
    public void setVisibilityInvestigatorChart(boolean isVisible) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isVisible) fragmentTransaction.show(investigatorChartFragment);
        else fragmentTransaction.hide(investigatorChartFragment);
        investigatorLL.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        fragmentTransaction.commit();
    }

    @Override
    public void finishActivity() {
        finish();
    }
}