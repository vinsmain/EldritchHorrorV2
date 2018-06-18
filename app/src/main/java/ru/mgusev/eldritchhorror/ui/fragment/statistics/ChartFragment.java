package ru.mgusev.eldritchhorror.ui.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.statistics.ChartPresenter;
import ru.mgusev.eldritchhorror.presentation.view.statistics.ChartView;
import ru.mgusev.eldritchhorror.ui.activity.statistics.Chart;

public class ChartFragment extends MvpAppCompatFragment implements ChartView {

    @BindView(R.id.chart_header) TextView chartHeader;
    @BindView(R.id.chart) Chart chart;

    @InjectPresenter
    ChartPresenter chartPresenter;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_card, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setData(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        chartHeader.setText(description);
        chart.setData(entries, labels, values, sum);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}