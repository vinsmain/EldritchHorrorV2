package ru.mgusev.eldritchhorror.ui.fragment.statistics;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ChartCardBinding;
import ru.mgusev.eldritchhorror.presentation.presenter.statistics.ChartPresenter;
import ru.mgusev.eldritchhorror.presentation.view.statistics.ChartView;
import ru.mgusev.eldritchhorror.ui.activity.statistics.Chart;

public class ChartFragment extends MvpAppCompatFragment implements ChartView {

    private ChartCardBinding binding;

    @InjectPresenter
    ChartPresenter chartPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChartCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void setData(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        binding.chartHeader.setText(description);
        binding.chart.setData(entries, labels, values, sum);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
