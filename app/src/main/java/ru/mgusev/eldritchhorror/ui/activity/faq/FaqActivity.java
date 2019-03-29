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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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

/**
 * Activity для вывода данных типа {@link Article}
 * Включает поиск по данным и ручное обновление данных
 */
public class FaqActivity extends OptionMenuSupportMvpAppCompatActivity implements FaqView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    /**
     * Репозиторий для работы с данными
     */
    @InjectPresenter
    FaqPresenter faqPresenter;
    /**
     * RecyclerView для вывода списка {@link Article}
     */
    @BindView(R.id.faq_rv)
    RecyclerView articleListRV;
    /**
     * Toolbar
     */
    @BindView(R.id.faq_toolbar)
    Toolbar toolbar;
    /**
     * Сообщение, выводимое если список {@link Article} пуст
     */
    @BindView(R.id.faq_none_results)
    TextView noneResultsTV;
    /**
     * Адаптер для {@link #articleListRV}
     */
    private FaqAdapter adapter;
    /**
     * Пункт меню "Обновить данные"
     */
    private MenuItem refreshItem;
    /**
     * АПункт меню "Поиск"
     */
    private MenuItem searchItem;
    /**
     * SearchView для поиска
     */
    private SearchView searchView;
    /**
     * Snackbar для вывода сообщений о успешной загрузке данных или об ошибке
     */
    private Snackbar alertSnackbar;

    /**
     * Инициирует {@link #toolbar}, {@link #articleListRV}, {@link #adapter}
     * @param savedInstanceState bundle
     */
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

    /**
     * Инициирует {@link #toolbar}
     */
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
     * getMvpDelegate().onAttach() необходим для присоединения {@link FaqActivity} к {@link FaqPresenter} только после создания меню
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

    /**
     * Принудительно вызывает getMvpDelegate().onAttach(), если Activity была восстановлена, а не создана с нуля
     * Условие добавлено для того, чтобы анимация для {@link #refreshItem} работала корректно после восстановления Activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (refreshItem != null) {
            getMvpDelegate().onAttach();
        }
    }

    /**
     * Принудительно вызывает getMvpDelegate().onAttach(), если Activity было восстановлена, а не создано с нуля
     * Условие добавлено для того, чтобы анимация для {@link #refreshItem} работала корректно после восстановления Activity
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (refreshItem != null) {
            getMvpDelegate().onAttach();
        }
    }

    /**
     * Обрабатывает нажатия на пункты меню
     * @param item пункт меню
     * @return параметр, подтверждающий нажатие на пункт меню
     */
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

    /**
     * Запускает анимацию вращения иконки {@link #refreshItem}
     */
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

    /**
     * Останавливает анимацию вращения иконки {@link #refreshItem}
     */
    @Override
    public void stopRefreshIconRotate() {
        Timber.d(String.valueOf(refreshItem));
        if (refreshItem != null && refreshItem.getActionView() != null) {
            refreshItem.getActionView().clearAnimation();
            refreshItem.setActionView(null);
        }
    }

    /**
     * Устанавливает новый список в {@link #adapter}
     * @param articleList список данных {@link Article}
     */
    @Override
    public void setDataToAdapter(List<Article> articleList) {
        Timber.d(String.valueOf(articleList.size()));
        adapter.setData(articleList);
    }

    /**
     * Устанавливает текст в поле поиска, нужно для сохранения состояния поля после смены конфигурации
     * Также убирается фокус с поля, чтобы не выводилась клавиатура после смены конфигурации
     * @param text текст для текстового поля
     */
    @Override
    public void setTextToSearchView(String text) {
        searchView.setQuery(text, false);
        searchView.clearFocus();
    }

    /**
     * Устанавливает состояние текстового поля, развернуто или свернуто
     * @param isExpanded значение состояния, true - развернуто, false - свернуто
     */
    @Override
    public void setExpandSearchView(boolean isExpanded) {
        Timber.d("onMenuItemActionExpand");
        if (isExpanded)
            searchItem.expandActionView();
        else
            searchItem.collapseActionView();
    }

    /**
     * Срабатывает, когда строка поиска разворачивается
     * @param item меню
     * @return true
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Timber.d("onMenuItemActionExpand");
        return true;
    }

    /**
     * Срабатывает, когда строка посика сворачивается
     * @param item меню
     * @return true
     */
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        faqPresenter.clickSearch("");
        Timber.d("onMenuItemActionCollapse");
        return true;
    }

    /**
     * Срабатывает, когда выполняется нажатие на кнопку выполнения поиска
     * @param s строка в поле поиска
     * @return false
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        faqPresenter.clickSearch(s);
        return false;
    }

    /**
     * Срабатывает, когда выполняется изменение текста в строке поиска
     * @param s текст в стрроке поиска
     * @return false
     */
    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    /**
     * Выводит {@link #alertSnackbar}
     * @param messageResId id строкового ресурса, который будет выведен
     */
    @Override
    public void showAlert(int messageResId) {
        alertSnackbar = Snackbar.make(articleListRV, messageResId, Snackbar.LENGTH_LONG);
        alertSnackbar.show();
    }

    /**
     * Управляет видимостью TextView {@link #noneResultsTV}
     * @param isVisible параметр, определяющий видимость
     */
    @Override
    public void setNoneResultsTVVisibility(boolean isVisible) {
        noneResultsTV.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * Сохраняет состояние строки поиска в {@link FaqPresenter} перед сменой конфигурации
     */
    @Override
    protected void onStop() {
        Timber.d(searchView.getQuery().toString());
        faqPresenter.changeExpandedSearchView(searchItem.isActionViewExpanded());
        faqPresenter.changeSearchText(searchView.getQuery().toString());
        super.onStop();
    }
}