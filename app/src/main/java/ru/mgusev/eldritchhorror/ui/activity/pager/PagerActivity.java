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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.PagerAdapter;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.PagerPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.PagerView;
import ru.mgusev.eldritchhorror.support.AndroidBug5497Workaround;

public class PagerActivity extends MvpAppCompatActivity implements PagerView {

    @InjectPresenter
    PagerPresenter pagerPresenter;

    @BindView(R.id.games_pager_head_background) ImageView headBackground;
    @BindView(R.id.games_pager_expansion_icon) ImageView expansionIcon;
    @BindView(R.id.games_pager_score) TextView scoreTV;
    @BindView(R.id.games_pager_win_icon) ImageView winIcon;
    @BindView(R.id.games_pager_toolbar) Toolbar toolbar;
    @BindView(R.id.games_pager_viewpager) ViewPager pager;
    @BindView(R.id.games_pager_add_photo) FloatingActionButton addPhotoButton;

    private int currentPosition = 0;
    private PagerAdapter pagerAdapter;
    private AlertDialog backDialog;
    private MenuItem actionRandom;
    private MenuItem actionClear;
    private MenuItem actionRandomSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_pager);
        ButterKnife.bind(this);
        AndroidBug5497Workaround.assistActivity(this);

        initToolbar();
        showAddPhotoButton();
        pagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("PAGER", "onPageSelected, position = " + position);
                currentPosition = position;
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
        if (currentPosition != 2 && view != null) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.dialogBackAlert);
        builder.setMessage(R.string.backDialogMessage);
        builder.setIcon(R.drawable.back_icon);
        builder.setPositiveButton(R.string.messageOK, (dialog, which) -> finishActivity());
        builder.setNegativeButton(R.string.messageCancel, (DialogInterface dialog, int which) -> pagerPresenter.dismissBackDialog());
        backDialog = builder.show();
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
        //Delete showBackDialog() from currentState with DismissDialogStrategy
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_games_pager_activity, menu);
        actionRandom = menu.findItem(R.id.action_random);
        actionClear = menu.findItem(R.id.action_clear);
        actionRandomSettings = menu.findItem(R.id.action_random_settings);
        showMenuItem();
        return true;
    }

    private void showMenuItem() {
        if (actionRandom != null) {
            if (currentPosition > 1) actionRandom.setVisible(false);
            else actionRandom.setVisible(true);
        }
        if (actionClear != null) {
            if (currentPosition == 1) {
                actionClear.setVisible(true);
                actionRandomSettings.setVisible(true);
            }
            else {
                actionClear.setVisible(false);
                actionRandomSettings.setVisible(false);
            }
        }
    }

    private void showAddPhotoButton() {
        if (currentPosition == 3) addPhotoButton.setVisibility(View.VISIBLE);
        else addPhotoButton.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                pagerPresenter.actionSave();
                return true;
            case R.id.action_clear:
                pagerPresenter.actionClear(currentPosition);
                return true;
            case R.id.action_random:
                pagerPresenter.actionRandom(currentPosition);
                return true;
            case R.id.action_random_settings:
                Intent randomSettingsIntent = new Intent(this, SpecializationChoiceActivity.class);
                startActivity(randomSettingsIntent);
                return true;
            case R.id.action_edit_expansion:
                Intent expansionChoiceIntent = new Intent(this, ExpansionChoiceActivity.class);
                startActivity(expansionChoiceIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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