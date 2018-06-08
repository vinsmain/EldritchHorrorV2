package ru.mgusev.eldritchhorror.ui.activity.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.MainAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.presenter.main.MainPresenter;
import ru.mgusev.eldritchhorror.presentation.view.main.MainView;
import ru.mgusev.eldritchhorror.support.ScrollListener;
import ru.mgusev.eldritchhorror.ui.activity.details.DetailsActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.PagerActivity;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnItemClicked {

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.main_start_message) TextView startMessageTV;
    @BindView(R.id.main_game_list) RecyclerView gameListRV;
    @BindView(R.id.main_toolbar) Toolbar toolbar;

    private MenuItem sortItem;
    private MenuItem authItem;
    private MenuItem statItem;

    private int columnsCount = 1;
    private MainAdapter adapter;
    private ScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnsCount = 2;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnsCount);
        //LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        gameListRV.setLayoutManager(gridLayoutManager);
        gameListRV.setHasFixedSize(true);

        adapter = new MainAdapter(this.getApplicationContext());
        gameListRV.setAdapter(adapter);
        adapter.setOnClick(this);
        scrollListener = new ScrollListener(adapter);
        gameListRV.addOnScrollListener(scrollListener);

        setSupportActionBar(toolbar);
        setTitle(R.string.main_header);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        sortItem = menu.findItem(R.id.action_sort);
        authItem = menu.findItem(R.id.action_auth);
        statItem = menu.findItem(R.id.action_statistics);
        /*if (currentUser == null) authItem.setIcon(R.drawable.google_icon);
        else setPhoto();
        setSortItemIcon();
        setVisibleStatItem();*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_about:
                Intent intent = new Intent(this, DonateActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_auth:
                if (currentUser != null) showPopupMenu(findViewById(R.id.action_auth));
                else signIn();
                return true;
            case R.id.action_sort:
                if (currentSortMode != SORT_MODE_SCORE_UP) currentSortMode++;
                else currentSortMode = SORT_MODE_DATE_DOWN;
                prefHelper.saveSortMode(currentSortMode);
                setSortItemIcon();
                initGameList();
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(this, StatisticsActivity.class);
                intentStatistics.putParcelableArrayListExtra("gameList", (ArrayList<? extends Parcelable>) gameList);
                startActivity(intentStatistics);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setDataToAdapter(List<Game> gameList) {
        adapter.updateGameList(gameList);
    }

    @Override
    public void showEmptyListMessage() {
        startMessageTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyListMessage() {
        startMessageTV.setVisibility(View.GONE);
    }

    @Override
    public void intentToPager() {
        Intent pagerIntent = new Intent(this, PagerActivity.class);
        startActivity(pagerIntent);
    }

    @Override
    public void intentToDetails() {
        Intent detailsIntent = new Intent(this, DetailsActivity.class);
        startActivity(detailsIntent);
    }

    @OnClick({R.id.main_add_game})
    public void onClick(View view) {
        mainPresenter.addGame();
        /*switch (view.getId()) {
            case R.id.main_activity_add_game:
                mainPresenter.addGame();
                break;
            default:
                break;
        }*/
    }

    @Override
    public void onItemClick(int position) {
        mainPresenter.setCurrentGame(position);
        intentToDetails();
    }

    @Override
    public void onEditClick(int position) {
        mainPresenter.setCurrentGame(position);
        intentToPager();
    }

    @Override
    public void onDeleteClick(int position) {
        mainPresenter.deleteGame(position);
    }

    @Override
    public void deleteGame(int position, List<Game> gameList) {
        adapter.deleteGame(position, gameList);
    }
}