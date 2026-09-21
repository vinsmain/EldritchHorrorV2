package ru.mgusev.eldritchhorror.ui.adapter.statistics;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mgusev.eldritchhorror.databinding.ChartCardBinding;
import ru.mgusev.eldritchhorror.presentation.model.statistics.StatisticsChart;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartViewHolder> {

    private final List<StatisticsChart> charts = new ArrayList<>();

    static class ChartViewHolder extends RecyclerView.ViewHolder {
        private final ChartCardBinding binding;

        ChartViewHolder(ChartCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setCharts(List<StatisticsChart> items) {
        charts.clear();
        charts.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChartViewHolder(ChartCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        StatisticsChart chart = charts.get(position);
        holder.binding.chartHeader.setText(chart.getDescription());
        holder.binding.chart.setData(chart.getEntries(), chart.getLabels(), chart.getValues(), chart.getSum());
    }

    @Override
    public int getItemCount() {
        return charts.size();
    }
}
