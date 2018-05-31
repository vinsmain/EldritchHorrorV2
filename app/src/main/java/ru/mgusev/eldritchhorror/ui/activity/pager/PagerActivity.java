package ru.mgusev.eldritchhorror.ui.activity.pager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Objects;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.model.Expansion;
import ru.mgusev.eldritchhorror.support.AndroidBug5497Workaround;
import ru.mgusev.eldritchhorror.adapter.PagerAdapter;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.PagerPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.PagerView;

public class PagerActivity extends MvpAppCompatActivity implements PagerView {

    @InjectPresenter
    PagerPresenter pagerPresenter;

    private int currentPosition = 0;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private ImageView headBackground;
    private ImageView expansionIcon;
    private ImageView winIcon;
    private TextView scoreTV;
    private Toolbar toolbar;
    private MenuItem actionRandom;
    private MenuItem actionClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_pager);
        //https://github.com/chenxiruanhai/AndroidBugFix/blob/master/bug-5497/AndroidBug5497Workaround.java
        AndroidBug5497Workaround.assistActivity(this);

        headBackground = findViewById(R.id.games_pager_head_background);
        expansionIcon = findViewById(R.id.games_pager_expansion_icon);
        winIcon = findViewById(R.id.games_pager_win_icon);
        scoreTV = findViewById(R.id.games_pager_score);
        toolbar = findViewById(R.id.games_pager_toolbar);
        initToolbar();

        pager = findViewById(R.id.games_pager_viewpager);
        pagerAdapter = new PagerAdapter(this, getSupportFragmentManager());
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(pagerAdapter);

        /*pager.postDelayed(new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem(0, false);
                pagerAdapter.notifyDataSetChanged();
            }
        }, 100);*/
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("PAGER", "onPageSelected, position = " + position);
                currentPosition = position;
                showMenuItem();
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
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_games_pager_activity, menu);
        actionRandom = menu.findItem(R.id.action_random);
        actionClear = menu.findItem(R.id.action_clear);
        showMenuItem();
        return true;
    }

    private void showMenuItem() {
        if (actionRandom != null) {
            if (currentPosition == 2) actionRandom.setVisible(false);
            else actionRandom.setVisible(true);
        }
        if (actionClear != null) {
            if (currentPosition == 1) actionClear.setVisible(true);
            else actionClear.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                /*if (((InvestigatorsChoiceFragment)pagerAdapter.getItem(1)).isStartingInvCountCorrect()) {
                    addDataToGame();
                    writeGameToDB();
                } else ((InvestigatorsChoiceFragment)pagerAdapter.getItem(1)).showStartingInvCountAlert();*/
                return true;
            case R.id.action_clear:
                pagerPresenter.actionClear(currentPosition);
                return true;
            case R.id.action_random:
                pagerPresenter.actionRandom(currentPosition);
                return true;
            case R.id.action_edit_expansion:
                Intent intent = new Intent(this, ExpansionChoiceActivity.class);
                startActivity(intent);
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
        winIcon.setImageDrawable(getResources().getDrawable(R.drawable.inestigators_out));
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


    //AndroidBug5497Workaround.assistActivity(this);
        //https://github.com/chenxiruanhai/AndroidBugFix/blob/master/bug-5497/AndroidBug5497Workaround.java

        //currentPosition = (int) getIntent().getIntExtra("setPosition", 0);

        /*if (savedInstanceState!= null) {
            currentPosition = savedInstanceState.getInt("position", 0);
            game = savedInstanceState.getParcelable("game");
            isAlert = savedInstanceState.getBoolean("DIALOG");
        }*/
        /*((NestedScrollView)findViewById(R.id.pager_scroll)).setFillViewport(true);
        pagerAdapter = new EHFragmentPagerAdapter (this, getSupportFragmentManager(), this);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(pagerAdapter);

        score = (TextView) findViewById(R.id.score_pager);

        if (game == null) game = (Game) getIntent().getParcelableExtra("editParty");
        if (game == null) game = new Game();

        score.setText(String.valueOf(game.score));

        if (game.id == -1) titleResource = R.string.add_new_game;
        else titleResource = R.string.edit_game;
        initToolbar();


        pager.postDelayed(new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem(currentPosition, false);
                pagerAdapter.notifyDataSetChanged();
                if (isAlert) ((InvestigatorsChoiceFragment)pagerAdapter.getItem(1)).cleanDialog();
            }
        }, 100);

        invalidateOptionsMenu();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
                currentPosition = position;

                if (position == 1 && clearItem != null) clearItem.setVisible(true);
                else if (clearItem != null) clearItem.setVisible(false);

                if (position != 2) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    if (randomItem != null) randomItem.setVisible(true);
                } else if (randomItem != null) randomItem.setVisible(false);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/


}
