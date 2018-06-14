package ru.mgusev.eldritchhorror.ui.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.statistics.ChartPresenter;
import ru.mgusev.eldritchhorror.presentation.view.statistics.ChartView;

public class ChartFragment extends MvpAppCompatFragment implements ChartView {

    @InjectPresenter
    ChartPresenter chartPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_card, container, false);
    }
}
