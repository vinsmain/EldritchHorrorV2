package ru.mgusev.eldritchhorror.presentation.model.statistics;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public class StatisticsChart {

    private final List<PieEntry> entries;
    private final String description;
    private final List<String> labels;
    private final List<Float> values;
    private final int sum;

    public StatisticsChart(List<PieEntry> entries, String description, List<String> labels, List<Float> values, int sum) {
        this.entries = entries;
        this.description = description;
        this.labels = labels;
        this.values = values;
        this.sum = sum;
    }

    public List<PieEntry> getEntries() {
        return entries;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Float> getValues() {
        return values;
    }

    public int getSum() {
        return sum;
    }
}
