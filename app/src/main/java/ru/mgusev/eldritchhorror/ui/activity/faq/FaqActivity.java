package ru.mgusev.eldritchhorror.ui.activity.faq;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.FaqAdapter;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.presentation.presenter.faq.FaqPresenter;
import ru.mgusev.eldritchhorror.presentation.view.faq.FaqView;

public class FaqActivity extends MvpAppCompatActivity implements FaqView, SearchView.OnQueryTextListener {

    @InjectPresenter
    FaqPresenter faqPresenter;

    @BindView(R.id.faq_rv)
    RecyclerView articleListRV;
    @BindView(R.id.faq_toolbar)
    Toolbar toolbar;

    private FaqAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        initToolbar();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        articleListRV.setLayoutManager(gridLayoutManager);
        //articleListRV.setHasFixedSize(true);

        adapter = new FaqAdapter();
        articleListRV.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.faq_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }


    @Override
    public void setDataToAdapter(List<Article> articleList) {
        adapter.updateAllFaqCards(articleList);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        faqPresenter.clickSearch(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
