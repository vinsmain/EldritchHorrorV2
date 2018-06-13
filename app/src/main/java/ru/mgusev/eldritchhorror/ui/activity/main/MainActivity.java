package ru.mgusev.eldritchhorror.ui.activity.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import ru.mgusev.eldritchhorror.ui.activity.about.AboutActivity;
import ru.mgusev.eldritchhorror.ui.activity.details.DetailsActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.PagerActivity;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnItemClicked {

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.main_start_message) TextView startMessageTV;
    @BindView(R.id.main_game_list) RecyclerView gameListRV;
    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.main_game_count) TextView gameCount;
    @BindView(R.id.main_best_score) TextView bestScore;
    @BindView(R.id.main_worst_score) TextView worstScore;

    private MenuItem sortItem;
    private MenuItem authItem;
    private MenuItem statItem;

    private int columnsCount = 1;
    private MainAdapter adapter;
    private ScrollListener scrollListener;
    private AlertDialog deleteDialog;

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
        mainPresenter.setSortModeIcon();
        /*if (currentUser == null) authItem.setIcon(R.drawable.google_icon);
        else setPhoto();
        setSortItemIcon();
        setVisibleStatItem();*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
           /* case R.id.action_auth:
                if (currentUser != null) showPopupMenu(findViewById(R.id.action_auth));
                else signIn();
                return true;*/
            case R.id.action_sort:
                mainPresenter.changeSortMode();
                return true;
            /*case R.id.action_statistics:
                Intent intentStatistics = new Intent(this, StatisticsActivity.class);
                intentStatistics.putParcelableArrayListExtra("gameList", (ArrayList<? extends Parcelable>) gameList);
                startActivity(intentStatistics);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setSortIcon(int sortMode) {
        switch (sortMode) {
            case 1:
                sortItem.setIcon(R.drawable.calendar_sort_down);
                sortItem.setTitle(R.string.sort_mode_message_1);
                break;
            case 2:
                sortItem.setIcon(R.drawable.calendar_sort_up);
                sortItem.setTitle(R.string.sort_mode_message_2);
                break;
            case 3:
                sortItem.setIcon(R.drawable.ancien_one_sort);
                sortItem.setTitle(R.string.sort_mode_message_3);
                break;
            case 4:
                sortItem.setIcon(R.drawable.score_sort_down);
                sortItem.setTitle(R.string.sort_mode_message_4);
                break;
            case 5:
                sortItem.setIcon(R.drawable.score_sort_up);
                sortItem.setTitle(R.string.sort_mode_message_5);
                break;
        }
    }

    @Override
    public void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.dialogAlert);
        builder.setMessage(R.string.deleteDialogMessage);
        builder.setIcon(R.drawable.delete);
        builder.setPositiveButton(R.string.messageOK, (dialog, which) -> {
            mainPresenter.deleteGame();
            mainPresenter.hideDeleteDialog();
        });
        builder.setNegativeButton(R.string.messageCancel, (DialogInterface dialog, int which) -> mainPresenter.hideDeleteDialog());
        deleteDialog = builder.show();
    }

    @Override
    public void hideDeleteDialog() {
        //Delete showDeleteDialog() from currentState with DismissDialogStrategy
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
        mainPresenter.showDeleteDialog(position);
    }

    @Override
    public void deleteGame(int position, List<Game> gameList) {
        adapter.deleteGame(position, gameList);
    }

    @Override
    public void setStatistics(int gameCount, int bestScore, int worstScore) {
        this.gameCount.setText(String.valueOf(gameCount));
        this.bestScore.setText(String.valueOf(bestScore));
        this.worstScore.setText(String.valueOf(worstScore));
    }

    @Override
    public void setStatistics(int gameCount) {
        this.gameCount.setText(String.valueOf(gameCount));
        this.bestScore.setText("");
        this.worstScore.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
    }
}