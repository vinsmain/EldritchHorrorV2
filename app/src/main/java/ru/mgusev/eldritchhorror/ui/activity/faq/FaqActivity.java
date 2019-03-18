package ru.mgusev.eldritchhorror.ui.activity.faq;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
import ru.mgusev.eldritchhorror.support.OptionMenuSupportMvpAppCompatActivity;
import timber.log.Timber;

public class FaqActivity extends OptionMenuSupportMvpAppCompatActivity implements FaqView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    @InjectPresenter
    FaqPresenter faqPresenter;

    @BindView(R.id.faq_rv)
    RecyclerView articleListRV;
    @BindView(R.id.faq_toolbar)
    Toolbar toolbar;

    private FaqAdapter adapter;
    private MenuItem refreshItem;
    private MenuItem searchItem;
    private SearchView searchView;
    private Snackbar errorSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        initToolbar();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        articleListRV.setLayoutManager(gridLayoutManager);

        adapter = new FaqAdapter(this);
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

    /**
     * Создает меню
     * getMvpDelegate().onAttach() необходим для своевременного присоединения view к FaqPresenter
     * Подробное описание {@link OptionMenuSupportMvpAppCompatActivity}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        Timber.d(String.valueOf(getMvpDelegate()));
        searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
        searchItem.setOnActionExpandListener(this);

        refreshItem = menu.findItem(R.id.refresh);

        getMvpDelegate().onAttach();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (refreshItem != null) {
            getMvpDelegate().onAttach();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (refreshItem != null) {
            getMvpDelegate().onAttach();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                faqPresenter.clickRefresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void startRefreshIconRotate() {
        Timber.d(String.valueOf(getMvpDelegate()));
        if (refreshItem != null) {
            /* Attach a rotating ImageView to the refresh item as an ActionView */
            LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView) inflater.inflate(R.layout.menu_item_refresh, null);

            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            rotation.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotation);

            refreshItem.setActionView(iv);
        }
    }

    @Override
    public void stopRefreshIconRotate() {
        Timber.d(String.valueOf(refreshItem));
        if (refreshItem != null && refreshItem.getActionView() != null) {
            refreshItem.getActionView().clearAnimation();
            refreshItem.setActionView(null);
        }
    }

    @Override
    public void setDataToAdapter(List<Article> articleList) {
        Timber.d(String.valueOf(articleList.size()));
        adapter.setData(articleList);
    }

    @Override
    public void setTextToSearchView(String text) {
        searchView.setQuery(text, false);
        searchView.clearFocus();
    }

    @Override
    public void setExpandSearchView(boolean isExpanded) {
        Timber.d("onMenuItemActionExpand");
        if (isExpanded)
            searchItem.expandActionView();
        else
            searchItem.collapseActionView();
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Timber.d("onMenuItemActionExpand");
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        faqPresenter.clickSearch("");
        Timber.d("onMenuItemActionCollapse");
        return true;
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

    @Override
    public void showError(int errorMessageResId) {
        errorSnackbar = Snackbar.make(articleListRV, errorMessageResId, Snackbar.LENGTH_LONG);
        errorSnackbar.show();
    }

    @Override
    protected void onStop() {
        Timber.d(searchView.getQuery().toString());
        faqPresenter.changeExpandedSearchView(searchItem.isActionViewExpanded());
        faqPresenter.changeSearchText(searchView.getQuery().toString());
        super.onStop();
    }
}