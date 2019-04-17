package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.RandomCardCategoryAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.model.CardType;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardCategoryPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardCategoryView;
import ru.mgusev.eldritchhorror.ui.activity.pager.ExpansionChoiceActivity;
import timber.log.Timber;

public class RandomCardCategoryActivity extends MvpAppCompatActivity implements RandomCardCategoryView, OnItemClickedReturnObj {

    @InjectPresenter
    RandomCardCategoryPresenter randomCardCategoryPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.random_card_category_rv)
    RecyclerView categoryListRV;

    private RandomCardCategoryAdapter adapter;
    private List<CardType> cardTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card_category);

        ButterKnife.bind(this);
        initToolbar();
        cardTypeList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return cardTypeList.get(position).getId() < 0 ? 2 : 1;
            }
        });

        categoryListRV.setLayoutManager(gridLayoutManager);

        adapter = new RandomCardCategoryAdapter(this);
        adapter.setOnClick(this);
        categoryListRV.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.random_card_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_random_card_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_expansion:
                //showLoader();
                Intent expansionChoiceIntent = new Intent(this, ExpansionChoiceActivity.class);
                startActivity(expansionChoiceIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setCategoryList(List<CardType> list) {
        cardTypeList = new ArrayList<>(list);
        adapter.setData(list);
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof CardType)
            randomCardCategoryPresenter.onCategoryClick((CardType) item);
    }

    @Override
    public void startRandomCardActivity() {
        startActivity(new Intent(this, RandomCardActivity.class));
    }
}
