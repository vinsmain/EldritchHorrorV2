package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.ExpansionChoiceAdapter;
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