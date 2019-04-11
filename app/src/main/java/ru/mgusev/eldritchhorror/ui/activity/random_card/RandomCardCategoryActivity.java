package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardCategoryPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardCategoryView;

public class RandomCardCategoryActivity extends MvpAppCompatActivity implements RandomCardCategoryView {

    @Inject
    RandomCardCategoryPresenter randomCardCategoryPresenter;

    @BindView(R.id.random_card_category_rv)
    RecyclerView categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card_category);

        ButterKnife.bind(this);
    }
}
