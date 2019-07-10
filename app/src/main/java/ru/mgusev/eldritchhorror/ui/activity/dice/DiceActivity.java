package ru.mgusev.eldritchhorror.ui.activity.dice;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;

import ru.mgusev.eldritchhorror.ui.adapter.dice.DiceAdapter;
import ru.mgusev.eldritchhorror.presentation.presenter.dice.DicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceView;
import timber.log.Timber;

public class DiceActivity extends MvpAppCompatActivity implements DiceView, SeekBar.OnSeekBarChangeListener {

    @InjectPresenter
    DicePresenter dicePresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dice_rv)
    RecyclerView diceListRV;
    @BindView(R.id.dice_count_seekbar)
    SeekBar diceCountSeekBar;
    @BindView(R.id.dice_reroll_all_fab)
    FloatingActionButton rerollAllFAB;
    @BindView(R.id.dice_success_count)
    TextView successCountTV;

    private DiceAdapter adapter;
    private int minDiceCount = 1;
    private int columnNumber = 3;
    private MenuItem screenLightOn;
    private MenuItem screenLightOff;
    private MenuItem animation2DMode;
    private MenuItem animation3DMode;
    private MenuItem diceCount;
    private MenuItem successMode6;
    private MenuItem successMode5;
    private MenuItem successMode4;
    private String successCountHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        ButterKnife.bind(this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnNumber = 5;

        initToolbar();

        diceCountSeekBar.setOnSeekBarChangeListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnNumber);

        diceListRV.setLayoutManager(gridLayoutManager);
        diceListRV.setHasFixedSize(true);

        adapter = new DiceAdapter(getMvpDelegate());
        diceListRV.setAdapter(adapter);

        diceListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && rerollAllFAB.getVisibility() == View.VISIBLE) {
                    rerollAllFAB.hide();
                } else if (dy < 0 && rerollAllFAB.getVisibility() != View.VISIBLE) {
                    rerollAllFAB.show();
                }
            }});

        successCountHeader = getResources().getString(R.string.number_of_successes_header);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.dice_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dice_activity, menu);
        screenLightOn = menu.findItem(R.id.action_screen_light_on);
        screenLightOff = menu.findItem(R.id.action_screen_light_off);
        animation2DMode = menu.findItem(R.id.action_2d_animation);
        animation3DMode = menu.findItem(R.id.action_3d_animation);
        diceCount = menu.findItem(R.id.dice_count);
        successMode6 = menu.findItem(R.id.action_success_mode_6);
        successMode5 = menu.findItem(R.id.action_success_mode_5);
        successMode4 = menu.findItem(R.id.action_success_mode_4);
        setDiceCountValue(String.valueOf(minDiceCount + diceCountSeekBar.getProgress()));
        setVisibilityScreenLightButtons();
        setVisibilityAnimationModeButtons();
        setVisibilitySuccessModeButtons();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_screen_light_on:
                dicePresenter.onScreenLightClick(false);
                return true;
            case R.id.action_screen_light_off:
                dicePresenter.onScreenLightClick(true);
                return true;
            case R.id.action_2d_animation:
                dicePresenter.onAnimationModeClick(true);
                return true;
            case R.id.action_3d_animation:
                dicePresenter.onAnimationModeClick(false);
                return true;
            case R.id.action_success_mode_6:
                dicePresenter.onSuccessModeClick(4);
                return true;
            case R.id.action_success_mode_5:
                dicePresenter.onSuccessModeClick(6);
                return true;
            case R.id.action_success_mode_4:
                dicePresenter.onSuccessModeClick(5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setVisibilityScreenLightButtons() {
        if (screenLightOn != null)
            screenLightOn.setVisible(dicePresenter.getScreenLight());
        if (screenLightOff != null)
            screenLightOff.setVisible(!dicePresenter.getScreenLight());
    }

    @Override
    public void setScreenLightFlags(boolean isScreenLightOn) {
        if (isScreenLightOn)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void setVisibilityAnimationModeButtons() {
        Timber.d(String.valueOf(dicePresenter.getAnimationMode()));
        if (animation3DMode != null)
            animation3DMode.setVisible(dicePresenter.getAnimationMode());
        if (animation2DMode != null)
            animation2DMode.setVisible(!dicePresenter.getAnimationMode());
    }

    @Override
    public void setVisibilitySuccessModeButtons() {
        if (successMode6 != null)
            successMode6.setVisible(dicePresenter.getSuccessMode() == 6);
        if (successMode5 != null)
            successMode5.setVisible(dicePresenter.getSuccessMode() == 5);
        if (successMode4 != null)
            successMode4.setVisible(dicePresenter.getSuccessMode() == 4);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        dicePresenter.onDiceCountSeekBarChangeProgress(minDiceCount + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        dicePresenter.onDiceCountSeekBarStopChangeProgress(minDiceCount + seekBar.getProgress());
    }

    @Override
    public void setInitialValueForSeekBar(int value) {
        diceCountSeekBar.setProgress(value - minDiceCount);
    }

    @Override
    public void setDiceCountValue(String value) {
        if (diceCount != null) {
            SpannableString spanString = new SpannableString(value);
            int end = spanString.length();
            spanString.setSpan(new RelativeSizeSpan(1.5f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            diceCount.setTitle(spanString);
        }
    }

    @Override
    public void setSuccessCount(String count) {
        String result = successCountHeader + count;
        successCountTV.setText(result);
    }

    @OnClick(R.id.dice_reroll_all_fab)
    public void onClick() {
        dicePresenter.onClickRerollAllBtn();
    }
}