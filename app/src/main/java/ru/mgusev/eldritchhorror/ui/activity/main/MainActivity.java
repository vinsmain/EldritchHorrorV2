package ru.mgusev.eldritchhorror.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityMainBinding;
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.ui.activity.ancient_one_info.AncientOneInfoActivity;
import ru.mgusev.eldritchhorror.ui.adapter.main.MainAdapter;
import ru.mgusev.eldritchhorror.model.Game;
import ru.mgusev.eldritchhorror.model.Localization;
import ru.mgusev.eldritchhorror.presentation.presenter.main.MainPresenter;
import ru.mgusev.eldritchhorror.presentation.view.main.MainView;
import ru.mgusev.eldritchhorror.utils.EdgeToEdge;
import ru.mgusev.eldritchhorror.utils.ScrollListener;
import ru.mgusev.eldritchhorror.ui.activity.about.AboutActivity;
import ru.mgusev.eldritchhorror.ui.activity.details.DetailsActivity;
import ru.mgusev.eldritchhorror.ui.activity.dice.DiceActivity;
import ru.mgusev.eldritchhorror.ui.activity.faq.FaqActivity;
import ru.mgusev.eldritchhorror.ui.activity.forgotten_endings.ForgottenEndingsActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.PagerActivity;
import ru.mgusev.eldritchhorror.ui.activity.random_card.RandomCardCategoryActivity;
import ru.mgusev.eldritchhorror.ui.activity.statistics.StatisticsActivity;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnItemClickedReturnObj, NavigationView.OnNavigationItemSelectedListener {

    public static boolean initialized;

    @InjectPresenter
    MainPresenter mainPresenter;

    private ActivityMainBinding binding;
    private TextView startMessageTV; private TextView gameCount; private TextView bestScore; private TextView worstScore; private RecyclerView gameListRV; private Toolbar toolbar; private FloatingActionButton addGameFAB; private DrawerLayout drawer; private NavigationView navigationView; private LinearLayout loader;

    private static final int RC_SIGN_IN = 9001;
    private static final String MUSIC_URL = "https://melodice.org/playlist/eldritch-horror-2013/";
    private static final String GOOGLE_PLAY_URL = "market://details?id=";

    private MenuItem sortItem;
    private MenuItem authItem;
    private MenuItem statItem;
    private MenuItem forgottenEndingsItem;
    private MenuItem infoAncientOneItem;

    private int columnsCount = 1;
    private MainAdapter adapter;
    private ScrollListener scrollListener;
    private AlertDialog deleteDialog;
    private AlertDialog rateDialog;
    private ActionBarDrawerToggle toggle;
    private HeaderViewHolder headerViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        startMessageTV = binding.mainStartMessage; gameCount = binding.mainGameCount; bestScore = binding.mainBestScore; worstScore = binding.mainWorstScore; gameListRV = binding.mainGameList; toolbar = binding.mainToolbar; addGameFAB = binding.mainAddGame; drawer = binding.mainDrawer; navigationView = binding.mainNavView; loader = (LinearLayout) binding.loader.getRoot();
        addGameFAB.setOnClickListener(this::onClick);
        EdgeToEdge.apply(this, toolbar, binding.mainContent);
        EdgeToEdge.applyHorizontalInsets(binding.mainSummary);
        EdgeToEdge.applyHorizontalInsets(gameListRV);
        EdgeToEdge.applyHorizontalInsets(startMessageTV);
        EdgeToEdge.applyHorizontalMarginInsets(addGameFAB);
        EdgeToEdge.applyNavigationMenuInsets(navigationView);
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

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View navigationHeader = navigationView.getHeaderView(0);
        EdgeToEdge.applyTopAndHorizontalInsets(navigationHeader);
        headerViewHolder = new HeaderViewHolder(navigationHeader);
        navigationView.setNavigationItemSelectedListener(this);

        initDrawerMenu();
        initStatisticHintsListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.clearGameInRepository();
    }

    private void initDrawerMenu() {
        infoAncientOneItem = navigationView.getMenu().getItem(1);
        forgottenEndingsItem = navigationView.getMenu().getItem(2);
        statItem = navigationView.getMenu().getItem(0);
        authItem = navigationView.getMenu().getItem(9);
        mainPresenter.setVisibilityStatisticsMenuItem();
        mainPresenter.initAuthMenuItem();
        setVisibilityForgottenEndingsItem();
        setVisibilityInfoAncientOneItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        sortItem = menu.findItem(R.id.action_sort);
        mainPresenter.setSortModeIcon();
        return true;
    }

    private void setVisibilityForgottenEndingsItem() {
        forgottenEndingsItem.setVisible(Localization.getInstance().isRusLocale());
    }

    private void setVisibilityInfoAncientOneItem() {
        infoAncientOneItem.setVisible(Localization.getInstance().isRusLocale());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
                mainPresenter.changeSortMode();
                return true;
        }
        if (item.getItemId() == R.id.action_donate) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        closeDrawer();
        if (itemId == R.id.action_about || itemId == R.id.action_donate) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (itemId == R.id.action_faq) {
            startActivity(new Intent(this, FaqActivity.class));
        } else if (itemId == R.id.action_auth) {
            mainPresenter.actionAuth();
        } else if (itemId == R.id.action_statistics) {
            startActivity(new Intent(this, StatisticsActivity.class));
        } else if (itemId == R.id.action_music) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MUSIC_URL)));
        } else if (itemId == R.id.action_forgotten_endings) {
            startActivity(new Intent(this, ForgottenEndingsActivity.class));
        } else if (itemId == R.id.action_random_card) {
            startActivity(new Intent(this, RandomCardCategoryActivity.class));
        } else if (itemId == R.id.action_dice) {
            startActivity(new Intent(this, DiceActivity.class));
        } else if (itemId == R.id.action_ancient_one_info) {
            startActivity(new Intent(this, AncientOneInfoActivity.class));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void changeAuthItem(boolean signedIn) {
        if (signedIn) {
            authItem.setTitle(R.string.sign_out_menu_header);
            authItem.setIcon(R.drawable.sign_out);
        } else {
            authItem.setTitle(R.string.auth_header);
            authItem.setIcon(R.drawable.sign_in);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoader();
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
        if (deleteDialog == null) {
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
    }

    @Override
    public void hideDeleteDialog() {
        deleteDialog = null;
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
        showLoader();
        Intent pagerIntent = new Intent(this, PagerActivity.class);
        startActivity(pagerIntent);
    }

    @Override
    public void intentToDetails() {
        showLoader();
        Intent detailsIntent = new Intent(this, DetailsActivity.class);
        startActivity(detailsIntent);
    }

    @Override
    public void intentToGooglePlay() {
        Uri uri = Uri.parse(GOOGLE_PLAY_URL + getPackageName()); // Go to Android market
        Intent googlePlayIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(googlePlayIntent);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.main_add_game) {
            mainPresenter.addGame();
        }
    }

    @SuppressLint("CheckResult")
    private void initStatisticHintsListener() {
        RxView.clicks(findViewById(R.id.main_count_game_linear)).throttleFirst(2000, TimeUnit.MILLISECONDS).subscribe(empty -> Toast.makeText(this, R.string.games_count, Toast.LENGTH_SHORT).show());
        RxView.clicks(findViewById(R.id.main_best_score_linear)).throttleFirst(2000, TimeUnit.MILLISECONDS).subscribe(empty -> Toast.makeText(this, R.string.best_result, Toast.LENGTH_SHORT).show());
        RxView.clicks(findViewById(R.id.main_worst_score_linear)).throttleFirst(2000, TimeUnit.MILLISECONDS).subscribe(empty -> Toast.makeText(this, R.string.worst_result, Toast.LENGTH_SHORT).show());
    }

    private void showLoader() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loader.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof Game) {
            mainPresenter.setCurrentGame((Game) item);
            intentToDetails();
        }
    }

    @Override
    public void onEditClick(Object item) {
        if (item instanceof Game) {
            mainPresenter.setCurrentGame((Game) item);
            intentToPager();
        }
    }

    @Override
    public void onDeleteClick(Object item) {
        if (item instanceof Game)
            mainPresenter.showDeleteDialog((Game) item);
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
        this.bestScore.setText("-");
        this.worstScore.setText("-");
    }

    @Override
    public void signIn(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Article returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            mainPresenter.startAuthTask(data);
        }
    }

    @Override
    public void setUserIcon(Uri iconUri) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(true);

        GenericDraweeHierarchy hierarchy =
                GenericDraweeHierarchyBuilder.newInstance(getResources())
                        .setFailureImage(R.drawable.user_signed)
                        .setPlaceholderImage(R.drawable.user_signed)
                        .setRoundingParams(roundingParams)
                        .build();
        headerViewHolder.iconImage.setHierarchy(hierarchy);
        headerViewHolder.iconImage.setImageURI(iconUri);
        Timber.d(String.valueOf(iconUri));
    }

    @Override
    public void setUserInfo(String name, String email) {
        headerViewHolder.nameTV.setText(name);
        headerViewHolder.emailTV.setText(email);
    }

    @Override
    public void showErrorSnackBar() {
        Snackbar.make(findViewById(R.id.main_drawer), R.string.auth_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
        if (rateDialog != null && rateDialog.isShowing()) rateDialog.dismiss();
    }
}
