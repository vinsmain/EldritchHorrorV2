package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.databinding.ActivitySpecializationChoiceBinding;
import ru.mgusev.eldritchhorror.ui.adapter.pager.SpecializationChoiceAdapter;
import ru.mgusev.eldritchhorror.model.Specialization;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.SpecializationChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.SpecializationChoiceView;

public class SpecializationChoiceActivity extends MvpAppCompatActivity implements SpecializationChoiceView {

    @InjectPresenter
    SpecializationChoicePresenter specializationChoicePresenter;

    private ActivitySpecializationChoiceBinding binding;

    private static int columnsCount = 1;
    private SpecializationChoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpecializationChoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void initSpecializationList(List<Specialization> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnsCount);
        binding.specializationChoiceRecyclerView.setLayoutManager(gridLayoutManager);
        binding.specializationChoiceRecyclerView.setHasFixedSize(true);
        adapter = new SpecializationChoiceAdapter(list);
        binding.specializationChoiceRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        specializationChoicePresenter.setTempList(adapter.getListStorage());
        super.onDestroy();
    }
}
