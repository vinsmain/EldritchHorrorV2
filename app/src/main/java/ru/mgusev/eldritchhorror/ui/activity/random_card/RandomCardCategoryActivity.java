package ru.mgusev.eldritchhorror.ui.activity.random_card;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityRandomCardCategoryBinding;
import ru.mgusev.eldritchhorror.ui.adapter.random_card.RandomCardCategoryAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClickedReturnObj;
import ru.mgusev.eldritchhorror.model.CardType;
import ru.mgusev.eldritchhorror.presentation.presenter.random_card.RandomCardCategoryPresenter;
import ru.mgusev.eldritchhorror.presentation.view.random_card.RandomCardCategoryView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import ru.mgusev.eldritchhorror.ui.activity.pager.ExpansionChoiceActivity;
import ru.mgusev.eldritchhorror.utils.EdgeToEdge;

public class RandomCardCategoryActivity extends MvpAppCompatActivity implements RandomCardCategoryView, OnItemClickedReturnObj {

    @InjectPresenter
    RandomCardCategoryPresenter randomCardCategoryPresenter;

    private ActivityRandomCardCategoryBinding binding;

    private RandomCardCategoryAdapter adapter;
    private List<CardType> cardTypeList;
    private int columnNumber = 2;
    private MenuItem screenLightOn;
    private MenuItem screenLightOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRandomCardCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.apply(this, binding.toolbar, binding.randomCardCategoryContent);
        EdgeToEdge.applyHorizontalInsets(binding.randomCardCategoryRv);
        EdgeToEdge.applyHorizontalInsets(binding.randomCardCategoryCopyright);

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(this, MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnNumber = 3;

        initToolbar();
        cardTypeList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnNumber);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return cardTypeList.get(position).getId() < 0 ? columnNumber : 1;
            }
        });

        binding.randomCardCategoryRv.setLayoutManager(gridLayoutManager);
        binding.randomCardCategoryRv.setHasFixedSize(true);

        adapter = new RandomCardCategoryAdapter(this);
        adapter.setOnClick(this);
        binding.randomCardCategoryRv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoader();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.random_card_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void showLoader() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        binding.loader.getRoot().setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        binding.loader.getRoot().setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_random_card_activity, menu);
        screenLightOn = menu.findItem(R.id.action_screen_light_on);
        screenLightOff = menu.findItem(R.id.action_screen_light_off);
        setVisibilityScreenLightButtons();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit_expansion) {
                showLoader();
                Intent expansionChoiceIntent = new Intent(this, ExpansionChoiceActivity.class);
                startActivity(expansionChoiceIntent);
                return true;
        } else if (item.getItemId() == R.id.action_screen_light_on) {
                randomCardCategoryPresenter.onScreenLightClick(false);
                return true;
        } else if (item.getItemId() == R.id.action_screen_light_off) {
                randomCardCategoryPresenter.onScreenLightClick(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setVisibilityScreenLightButtons() {
        if (screenLightOn != null)
            screenLightOn.setVisible(randomCardCategoryPresenter.getScreenLight());
        if (screenLightOff != null)
            screenLightOff.setVisible(!randomCardCategoryPresenter.getScreenLight());
    }

    @Override
    public void setCategoryList(List<CardType> list) {
        cardTypeList = new ArrayList<>(list);
        adapter.setData(list);
    }

    @Override
    public void onItemClick(Object item) {
        if (item instanceof CardType)
            randomCardCategoryPresenter.onCategoryClick((CardType) item);
    }

    @Override
    public void onEditClick(Object item) {
        //Pass
    }

    @Override
    public void onDeleteClick(Object item) {
        //Pass
    }

    @Override
    public void startRandomCardActivity() {
        startActivity(new Intent(this, RandomCardActivity.class));
    }

    @Override
    public void setScreenLightFlags(boolean isScreenLightOn) {
        if (isScreenLightOn)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
