package ru.mgusev.eldritchhorror.ui.activity.main;

import android.arch.lifecycle.LifecycleOwner;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.Arrays;
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
import ru.mgusev.eldritchhorror.support.IconizedMenu;
import ru.mgusev.eldritchhorror.support.ScrollListener;
import ru.mgusev.eldritchhorror.ui.activity.about.AboutActivity;
import ru.mgusev.eldritchhorror.ui.activity.details.DetailsActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.PagerActivity;
import ru.mgusev.eldritchhorror.ui.activity.statistics.StatisticsActivity;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnItemClicked, LifecycleOwner {

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.main_start_message) TextView startMessageTV;
    @BindView(R.id.main_game_list) RecyclerView gameListRV;
    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.main_game_count) TextView gameCount;
    @BindView(R.id.main_best_score) TextView bestScore;
    @BindView(R.id.main_worst_score) TextView worstScore;

    private static final int RC_SIGN_IN = 9001;

    private Menu menu;
    private MenuItem sortItem;
    private MenuItem authItem;
    private MenuItem statItem;

    private int columnsCount = 1;
    private MainAdapter adapter;
    private ScrollListener scrollListener;
    private AlertDialog deleteDialog;
    private IconizedMenu popup;
    private MenuPopupHelper optionsMenu;
    private CustomPowerMenu powerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
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
        this.menu = menu;
        sortItem = menu.findItem(R.id.action_sort);
        authItem = menu.findItem(R.id.action_auth);
        statItem = menu.findItem(R.id.action_statistics);
        mainPresenter.setSortModeIcon();
        mainPresenter.setVisibilityStatisticsMenuItem();
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
            case R.id.action_auth:
                mainPresenter.actionAuth();
                return true;
            case R.id.action_sort:
                mainPresenter.changeSortMode();
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(this, StatisticsActivity.class);
                startActivity(intentStatistics);
                return true;
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
    public void showStatisticsMenuItem() {
        if (statItem != null) statItem.setVisible(true);
    }

    @Override
    public void hideStatisticsMenuItem() {
        if (statItem != null) statItem.setVisible(false);
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
    public void showSignOutMenu() {

        powerMenu = new CustomPowerMenu<>.Builder(this)
                //.addItem(new IconPowerMenuItem(context.getResources().getDrawable(R.drawable.ic_twitter), "Twitter"))
                .addItem(new PowerMenuItem("Travel", false))
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(this.getResources().getColor(R.color.colorAccent))
                .setSelectedTextColor(Color.BLUE)
                .setMenuColor(Color.WHITE)
                .setSelectedMenuColor(this.getResources().getColor(R.color.colorPrimary))
                .setLifecycleOwner(this)
                //.setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
        powerMenu.showAsDropDown(findViewById(R.id.action_auth));*/
    }

    @Override
    public void signIn(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void signOut() {
        mainPresenter.signOut();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            mainPresenter.startAuthTask(data);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
            finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (popup != null) {
            System.out.println(popup);
            //menu.close();
           // menu.clear();
            //popup.dismiss();
            //popup.onDestroy();
            //popup.clear();
            System.out.println(popup);



        }
        for (int i = 0; i <10000; i++) {
            System.out.println(i + " " + popup);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();



    }
}