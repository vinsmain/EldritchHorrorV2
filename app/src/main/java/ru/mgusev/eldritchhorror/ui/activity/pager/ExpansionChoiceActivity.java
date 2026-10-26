package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.databinding.ActivityExpansionChoiceBinding;
import ru.mgusev.eldritchhorror.ui.adapter.pager.ExpansionChoiceAdapter;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ExpansionChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.ExpansionChoiceView;

public class ExpansionChoiceActivity extends MvpAppCompatActivity implements ExpansionChoiceView {

    @InjectPresenter
    ExpansionChoicePresenter pagerPresenter;

    private ActivityExpansionChoiceBinding binding;

    private static int columnsCount = 1;
    private ExpansionChoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpansionChoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void initExpansionList(List<Expansion> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnsCount);
        binding.expansionChoiceRecyclerView.setLayoutManager(gridLayoutManager);
        binding.expansionChoiceRecyclerView.setHasFixedSize(true);
        adapter = new ExpansionChoiceAdapter(list);
        binding.expansionChoiceRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        pagerPresenter.setTempList(adapter.getListStorage());
        super.onDestroy();
    }
}
