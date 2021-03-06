package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.ui.adapter.pager.SpecializationChoiceAdapter;
import ru.mgusev.eldritchhorror.model.Specialization;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.SpecializationChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.SpecializationChoiceView;

public class SpecializationChoiceActivity extends MvpAppCompatActivity implements SpecializationChoiceView {

    @InjectPresenter
    SpecializationChoicePresenter specializationChoicePresenter;

    @BindView(R.id.specialization_choice_recycler_view) RecyclerView specializationRV;

    private static int columnsCount = 1;
    private SpecializationChoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialization_choice);
        ButterKnife.bind(this);
    }

    @Override
    public void initSpecializationList(List<Specialization> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnsCount);
        specializationRV.setLayoutManager(gridLayoutManager);
        specializationRV.setHasFixedSize(true);
        adapter = new SpecializationChoiceAdapter(list);
        specializationRV.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        specializationChoicePresenter.setTempList(adapter.getListStorage());
        super.onDestroy();
    }
}