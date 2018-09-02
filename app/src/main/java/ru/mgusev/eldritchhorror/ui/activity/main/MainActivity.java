package ru.mgusev.eldritchhorror.ui.activity.main;

import android.arch.lifecycle.LifecycleOwner;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.skydoves.powermenu.CustomPowerMenu;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.IconMenuAdapter;
import ru.mgusev.eldritchhorror.adapter.MainAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.presentation.presenter.main.MainPresenter;
import ru.mgusev.eldritchhorror.presentation.view.main.MainView;
import ru.mgusev.eldritchhorror.support.IconPowerMenuItem;
import ru.mgusev.eldritchhorror.support.ScrollListener;
import ru.mgusev.eldritchhorror.ui.activity.about.AboutActivity;
import ru.mgusev.eldritchhorror.ui.activity.details.DetailsActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.PagerActivity;
import ru.mgusev.eldritchhorror.ui.activity.statistics.StatisticsActivity;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnItemClicked, LifecycleOwner, OnMenuItemClickListener {

    public static boolean initialized;

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.main_start_message) TextView startMessageTV;
    @BindView(R.id.main_game_list) RecyclerView gameListRV;
    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.main_game_count) TextView gameCount;
    @BindView(R.id.main_best_score) TextView bestScore;
    @BindView(R.id.main_worst_score) TextView worstScore;

    private static final int RC_SIGN_IN = 9001;
    private static final String MUSIC_URL = "https://melodice.org/playlist/eldritch-horror-2013/";
    private static final String GOOGLE_PLAY_URL = "market://details?id=";

    private MenuItem sortItem;
    private MenuItem authItem;
    private MenuItem statItem;
    private CustomPowerMenu authPopupMenu;

    private int columnsCount = 1;
    private MainAdapter adapter;
    private ScrollListener scrollListener;
    private AlertDialog deleteDialog;
    private AlertDialog rateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialized = true;

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
    protected void onResume() {
        super.onResume();
        //mainPresenter.deleteDraftGames();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        sortItem = menu.findItem(R.id.action_sort);
        authItem = menu.findItem(R.id.action_auth);
        statItem = menu.findItem(R.id.action_statistics);
        mainPresenter.setSortModeIcon();
        mainPresenter.setVisibilityStatisticsMenuItem();
        mainPresenter.startUpdateUserIcon();
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
            case R.id.action_music:
                Intent browserIntentYandex = new Intent(Intent.ACTION_VIEW, Uri.parse(MUSIC_URL));
                startActivity(browserIntentYandex);
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
    public void showRateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.rate_title);
        builder.setMessage(R.string.rate_massage);
        builder.setIcon(R.drawable.rate_star);
        builder.setPositiveButton(R.string.messageOK, (dialog, which) -> {
            intentToGooglePlay();
            mainPresenter.hideRateDialog();
            mainPresenter.setRateResult(true);
        });
        builder.setNegativeButton(R.string.message_later, (DialogInterface dialog, int which) -> {
            mainPresenter.hideRateDialog();
            mainPresenter.setRateResult(false);
        });
        rateDialog = builder.show();
    }

    @Override
    public void hideRateDialog() {
        //Delete showRateDialog() from currentState with DismissDialogStrategy
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

    @Override
    public void intentToGooglePlay() {
        Uri uri = Uri.parse(GOOGLE_PLAY_URL + getPackageName()); // Go to Android market
        Intent googlePlayIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(googlePlayIntent);
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
    public void onItemLongClick(int position) {

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
        authPopupMenu = new CustomPowerMenu.Builder<>(this, new IconMenuAdapter())
                .addItem(new IconPowerMenuItem(getResources().getDrawable(R.drawable.sign_out), getResources().getString(R.string.sign_out_menu_header)))
                .setOnMenuItemClickListener(this)
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setMenuRadius(0f)
                .setMenuShadow(10f)
                .setLifecycleOwner(this)
                .build();
        authPopupMenu.showAsDropDown(findViewById(R.id.action_auth));
    }

    @Override
    public void onItemClick(int position, Object item) {
        mainPresenter.signOut();
        authPopupMenu.dismiss();
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
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            mainPresenter.startAuthTask(data);
        }
    }

    @Override
    public void setUserIcon(Drawable icon) {
        if (authItem != null) authItem.setIcon(icon);
    }

    @Override
    public void showErrorSnackBar() {
        Snackbar.make(findViewById(R.id.main_coordinator), R.string.auth_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
        if (rateDialog != null && rateDialog.isShowing()) rateDialog.dismiss();
    }
}