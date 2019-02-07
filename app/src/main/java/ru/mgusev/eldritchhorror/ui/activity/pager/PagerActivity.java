package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.PagerAdapter;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.PagerPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.PagerView;
import ru.mgusev.eldritchhorror.support.AndroidBug5497Workaround;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

public class PagerActivity extends MvpAppCompatActivity implements PagerView {

    @InjectPresenter
    PagerPresenter pagerPresenter;

    @BindView(R.id.games_pager_head_background) ImageView headBackground;
    @BindView(R.id.games_pager_expansion_icon) ImageView expansionIcon;
    @BindView(R.id.games_pager_score) TextView scoreTV;
    @BindView(R.id.games_pager_win_icon) ImageView winIcon;
    @BindView(R.id.games_pager_toolbar) Toolbar toolbar;
    @BindView(R.id.games_pager_viewpager) ViewPager pager;
    @BindView(R.id.games_pager_add_image) FabSpeedDial addImageFab;
    @BindView(R.id.games_pager_delete_image) FloatingActionButton deleteImageFab;
    @BindView(R.id.loader) LinearLayout loader;

    private PagerAdapter pagerAdapter;
    private AlertDialog backDialog;
    private MenuItem actionEditExpansion;
    private MenuItem actionRandom;
    private MenuItem actionClear;
    private MenuItem actionRandomSettings;
    private MenuItem actionSelectAll;
    private MenuItem actionSelectCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_pager);
        ButterKnife.bind(this);
        AndroidBug5497Workaround.assistActivity(this);

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(this, MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

        initToolbar();
        showAddPhotoButton();
        hideKeyboard();
        pagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Timber.d("onPageSelected, position = %s", position);
                pagerPresenter.setCurrentPosition(position);
                showMenuItem();
                showAddPhotoButton();
                hideKeyboard();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        addImageFab.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                switch (menuItem.getItemId()) {
                    case R.id.action_camera:
                        pagerPresenter.clickOnAddPhotoButton(true);
                        return false;
                    case R.id.action_gallery:
                        pagerPresenter.clickOnAddPhotoButton(false);
                        return false;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoader();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.add_new_game);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> pagerPresenter.showBackDialog());
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void setEditToolbarHeader() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.edit_game);
    }

    @Override
    public void setCurrentPosition(int position) {
        pager.setCurrentItem(position, false);
    }

    @Override
    public void showBackDialog() {
        if (backDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(R.string.dialogBackAlert);
            builder.setMessage(R.string.backDialogMessage);
            builder.setIcon(R.drawable.back_icon);
            builder.setPositiveButton(R.string.messageOK, (DialogInterface dialog, int which) -> {
                pagerPresenter.deleteFilesIfGameNotCreated();
                finishActivity();
            });
            builder.setNegativeButton(R.string.messageCancel, (DialogInterface dialog, int which) -> pagerPresenter.dismissBackDialog());
            backDialog = builder.show();
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.alert_investigators_limit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideBackDialog() {
        backDialog = null;
        //Delete showBackDialog() from currentState with DismissDialogStrategy
    }

    @Override
    public void clearViewState() {
        //None, just clear view state queue
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_games_pager_activity, menu);
        actionRandom = menu.findItem(R.id.action_random);
        actionEditExpansion = menu.findItem(R.id.action_edit_expansion);
        actionClear = menu.findItem(R.id.action_clear);
        actionRandomSettings = menu.findItem(R.id.action_random_settings);
        actionSelectAll = menu.findItem(R.id.action_select_all);
        actionSelectCancel = menu.findItem(R.id.action_select_cancel);
        showMenuItem();
        return true;
    }

    private void showMenuItem() {
        int currentPosition = pagerPresenter.getCurrentPosition();
        if (actionRandom != null) actionRandom.setVisible(currentPosition <= 1);
        if (actionClear != null) actionClear.setVisible(currentPosition == 1);
        if (actionRandomSettings != null) actionRandomSettings.setVisible(currentPosition == 1);
        if (actionSelectAll != null) actionSelectAll.setVisible(currentPosition == 3);
        if (actionEditExpansion != null) actionEditExpansion.setVisible(currentPosition != 3);

        showActionSelectCancelButton();
    }

    private void showAddPhotoButton() {
        if (pagerPresenter.getCurrentPosition() != 3) pagerPresenter.clickSelectPhoto(false);
        addImageFab.setVisibility(pagerPresenter.getCurrentPosition() == 3 ? View.VISIBLE : View.GONE);
        if (pagerPresenter.getCurrentPosition() != 3) deleteImageFab.setVisibility(View.GONE);
    }

    @Override
    public void setAddPhotoButtonIcon(boolean selectedMode) {
        if (pagerPresenter.getCurrentPosition() == 3) {
            if (selectedMode) {
                deleteImageFab.setVisibility(View.VISIBLE);
                addImageFab.closeMenu();
                addImageFab.setVisibility(View.GONE);
            } else {
                deleteImageFab.setVisibility(View.GONE);
                addImageFab.setVisibility(View.VISIBLE);
            }
        }
        showActionSelectCancelButton();
    }

    @OnClick({R.id.games_pager_delete_image})
    public void onClick(View view) {
        pagerPresenter.clickOnDeletePhotoButton();
    }

    private void showActionSelectCancelButton() {
        if (actionSelectCancel != null) {
            if (pagerPresenter.getCurrentPosition() == 3) actionSelectCancel.setVisible(pagerPresenter.isSelectedMode());
            else actionSelectCancel.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                pagerPresenter.actionSave();
                return true;
            case R.id.action_clear:
                pagerPresenter.actionClear(pagerPresenter.getCurrentPosition());
                return true;
            case R.id.action_random:
                pagerPresenter.actionRandom(pagerPresenter.getCurrentPosition());
                return true;
            case R.id.action_random_settings:
                showLoader();
                Intent randomSettingsIntent = new Intent(this, SpecializationChoiceActivity.class);
                startActivity(randomSettingsIntent);
                return true;
            case R.id.action_edit_expansion:
                showLoader();
                Intent expansionChoiceIntent = new Intent(this, ExpansionChoiceActivity.class);
                startActivity(expansionChoiceIntent);
                return true;
            case R.id.action_select_all:
                pagerPresenter.clickSelectPhoto(true);
                return true;
            case R.id.action_select_cancel:
                pagerPresenter.clickSelectPhoto(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showLoader() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loader.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        loader.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void setHeadBackground(AncientOne ancientOne, Expansion expansion) {
        headBackground.setImageResource(getResources().getIdentifier(ancientOne.getImageResource(), "drawable", getPackageName()));
        expansionIcon.setImageResource(getResources().getIdentifier(expansion.getImageResource(), "drawable", getPackageName()));
    }

    @Override
    public void setScore(int score) {
        scoreTV.setText(String.valueOf(score));
    }

    @Override
    public void setWinIcon() {
        winIcon.setImageDrawable(getResources().getDrawable(R.drawable.stars));
    }

    @Override
    public void setDefeatByEliminationIcon() {
        winIcon.setImageDrawable(getResources().getDrawable(R.drawable.investigators_out));
    }

    @Override
    public void setDefeatByMythosDepletionIcon() {
        winIcon.setImageDrawable(getResources().getDrawable(R.drawable.mythos_empty));
    }

    @Override
    public void setDefeatByAwakenedAncientOneIcon() {
        winIcon.setImageDrawable(getResources().getDrawable(R.drawable.skull));
    }

    @Override
    public void setDefeatByRumorIcon() {
        winIcon.setImageDrawable(getResources().getDrawable(R.drawable.defeat_by_rumor));
    }

    @Override
    public void setDefeatBySurrenderIcon() {
        winIcon.setImageDrawable(getResources().getDrawable(R.drawable.defeat_by_surrender));
    }

    @Override
    public void showScore() {
        scoreTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideScore() {
        scoreTV.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        pagerPresenter.showBackDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(backDialog != null && backDialog.isShowing()) backDialog.dismiss();
    }
}