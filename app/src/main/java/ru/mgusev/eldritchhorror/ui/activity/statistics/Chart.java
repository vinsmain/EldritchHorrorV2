package ru.mgusev.eldritchhorror.ui.activity.statistics;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ru.mgusev.eldritchhorror.R;

public class Chart extends PieChart implements OnChartValueSelectedListener {

    private List<Integer> colors;
    private Legend legend;
    private PieDataSet dataSet;
    private PieData data;
    private Description description;
    private List<String> labels;
    private List<Float> values;
    private int count;

    public Chart(Context context) {
        super(context);
        this.setOnChartValueSelectedListener(this);
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnChartValueSelectedListener(this);
    }

    public Chart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnChartValueSelectedListener(this);
    }

    private void initColors() {
        colors = new ArrayList<>();

        colors.add(Color.rgb(96,125,139));
        colors.add(Color.rgb(63,81,181));
        colors.add(Color.rgb(139,195,74));
        colors.add(Color.rgb(33,150,243));
        colors.add(Color.rgb(0,150,136));
        colors.add(Color.rgb(76,175,80));
        colors.add(Color.rgb(0,188,212));
        colors.add(Color.rgb(121,134,203));
        colors.add(Color.rgb(174,213,129));
        colors.add(Color.rgb(100,181,246));
        colors.add(Color.rgb(129,199,132));
        colors.add(Color.rgb(77,208,225));
        colors.add(Color.rgb(77,182,172));
        colors.add(Color.rgb(144,164,174));
        colors.add(Color.rgb(81,45,168));
        colors.add(Color.rgb(149,117,205));

        /*
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());
        */
    }

    private void initLegend() {
        legend = getLegend();
        legend.setFormSize(18f); // set the size of the legend forms/shapes
        legend.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(5f);
        legend.setWordWrapEnabled(true);
    }

    private void initDescription() {
        description = new Description();
        description.setText("");
    }

    private void initPieData() {
        data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14);
        data.setValueTextColor(Color.BLACK);
    }

    private void initPieChart() {
        setData(data);
        setNoDataText(getResources().getString(R.string.statistics_none));
        setNoDataTextColor(Color.BLACK);
        setDescription(description);
        setEntryLabelColor(Color.BLACK);
        setHoleRadius(60f);
        setTransparentCircleRadius(65f);
        animateY(1000, Easing.EasingOption.EaseInOutQuad);
    }

    public void setData(List<PieEntry> entries, List<String> labels, List<Float> values, int sum) {
        this.labels = labels;
        this.values = values;
        this.count = sum;
        initColors();
        initLegend();
        initDescription();
        dataSet = new PieDataSet(entries, "");
        // add a lot of colors
        dataSet.setColors(colors);
        initPieData();
        initPieChart();
        setCenterTextSize(16);
        setDrawEntryLabels(false);
        if (!entries.isEmpty()) highlightValue(0f, 0, true);
        this.setMinimumHeight((int) (1000 + legend.mNeededHeight));
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        setCenterText(labels.get((int) h.getX()) + "\n" + new DecimalFormat("#0.0").format(e.getY()) + "%\n" + Math.round(values.get((int) h.getX())) + "/" + count);
    }

    @Override
    public void onNothingSelected() {
        this.setCenterText("");
    }
}