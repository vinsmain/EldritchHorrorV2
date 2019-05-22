package ru.mgusev.eldritchhorror.ui.activity.dice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.adapter.DiceAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.model.Dice;
import ru.mgusev.eldritchhorror.presentation.presenter.dice.DicePresenter;
import ru.mgusev.eldritchhorror.presentation.view.dice.DiceView;
import timber.log.Timber;

public class DiceActivity extends MvpAppCompatActivity implements DiceView, SeekBar.OnSeekBarChangeListener, OnItemClickedReturnObj {

    @InjectPresenter
    DicePresenter dicePresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dice_rv)
    RecyclerView diceListRV;
    @BindView(R.id.dice_count_seekbar)
    SeekBar diceCountSeekBar;
    @BindView(R.id.loader)
    LinearLayout loader;

    private DiceAdapter adapter;
    private int minDiceCount = 1;
    private MenuItem screenLightOn;
    private MenuItem screenLightOff;
    private MenuItem diceCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        ButterKnife.bind(this);

        initToolbar();

        diceCountSeekBar.setOnSeekBarChangeListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        diceListRV.setLayoutManager(gridLayoutManager);
        diceListRV.setHasFixedSize(true);

        adapter = new DiceAdapter();
        adapter.setOnClick(this);
        diceListRV.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Кости");
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
        diceCount = menu.findItem(R.id.dice_count);
        setDiceCountValue(String.valueOf(minDiceCount + diceCountSeekBar.getProgress()));
        setVisibilityScreenLightButtons();
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
    public void updateDiceList(List<Dice> diceList) {
        adapter.clearAndAddAll(diceList);
    }

    @OnClick(R.id.dice_reroll_all_btn)
    public void onClick() {
        dicePresenter.onClickRerollAllBtn();
    }

    @Override
    public void startAnimation() {
        //adapter.setAnimation(true);
    }

    @Override
    public void stopAnimation() {
        //adapter.setAnimation(false);
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof Dice) {
            dicePresenter.onClickRerollOneDice((Dice) item);
        }
    }
}