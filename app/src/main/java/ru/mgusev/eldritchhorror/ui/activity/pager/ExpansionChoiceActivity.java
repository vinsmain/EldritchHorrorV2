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
import ru.mgusev.eldritchhorror.ui.adapter.pager.ExpansionChoiceAdapter;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ExpansionChoicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.ExpansionChoiceView;

public class ExpansionChoiceActivity extends MvpAppCompatActivity implements ExpansionChoiceView {

    @InjectPresenter
    ExpansionChoicePresenter pagerPresenter;

    @BindView(R.id.expansion_choice_recycler_view) RecyclerView expansionRV;

    private static int columnsCount = 1;
    private ExpansionChoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansion_choice);
        ButterKnife.bind(this);
    }

    @Override
    public void initExpansionList(List<Expansion> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnsCount);
        expansionRV.setLayoutManager(gridLayoutManager);
        expansionRV.setHasFixedSize(true);
        adapter = new ExpansionChoiceAdapter(list);
        expansionRV.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        pagerPresenter.setTempList(adapter.getListStorage());
        super.onDestroy();
    }
}