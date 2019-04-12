package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.RandomCardCategoryAdapter;
import ru.mgusev.eldritchhorror.model.ConditionType;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardCategoryPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardCategoryView;

public class RandomCardCategoryActivity extends MvpAppCompatActivity implements RandomCardCategoryView {

    @InjectPresenter
    RandomCardCategoryPresenter randomCardCategoryPresenter;

    @BindView(R.id.random_card_category_rv)
    RecyclerView categoryListRV;

    private RandomCardCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card_category);

        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        categoryListRV.setLayoutManager(gridLayoutManager);

        adapter = new RandomCardCategoryAdapter(this);
        categoryListRV.setAdapter(adapter);
    }

    @Override
    public void setCategoryList(List<ConditionType> list) {
        adapter.setData(list);
    }
}
