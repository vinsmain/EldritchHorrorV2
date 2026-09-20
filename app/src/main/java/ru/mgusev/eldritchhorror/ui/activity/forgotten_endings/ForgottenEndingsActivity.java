package ru.mgusev.eldritchhorror.ui.activity.forgotten_endings;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.tiper.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.ActivityForgottenEndingsBinding;
import ru.mgusev.eldritchhorror.utils.EdgeToEdge;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.presentation.presenter.forgotten_endings.ForgottenEndingsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.forgotten_endings.ForgottenEndingsView;
import timber.log.Timber;

public class ForgottenEndingsActivity extends MvpAppCompatActivity implements ForgottenEndingsView, CompoundButton.OnCheckedChangeListener, MaterialSpinner.OnItemSelectedListener {

    private ActivityForgottenEndingsBinding binding;

    @InjectPresenter
    ForgottenEndingsPresenter forgottenEndingsPresenter;

    Toolbar toolbar;
    NestedScrollView scrollView;
    MaterialSpinner ancientOneSpinner;
    AppCompatImageView imageAncientOne;
    Switch resultSwitch;
    LinearLayout conditionsContainer;
    Button readTextBtn;
    TextView headerTV;
    TextView textTV;

    private ArrayAdapter<String> ancientOneAdapter;
    private boolean skipCheckedChangedSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgottenEndingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.toolbar;
        scrollView = binding.forgottenEndingsScrollView;
        ancientOneSpinner = binding.forgottenEndingsAncientOneSpinner;
        imageAncientOne = binding.imageAncientOne;
        resultSwitch = binding.forgottenEndingsResultSwitch;
        conditionsContainer = binding.forgottenEndingsSwitchContainer;
        readTextBtn = binding.forgottenEndingsReadTextBtn;
        headerTV = binding.forgottenEndingsHeaderTv;
        textTV = binding.forgottenEndingsTextTv;
        resultSwitch.setOnCheckedChangeListener(this::onCheckedChanged);
        readTextBtn.setOnClickListener(v -> onClick());
        EdgeToEdge.apply(this, toolbar, binding.forgottenEndingsContent);

        initToolbar();
        initAncientOneSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        forgottenEndingsPresenter.setSpinnerPosition();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.forgotten_endings_header);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initAncientOneSpinner() {
        ancientOneAdapter = new ArrayAdapter<>(this, R.layout.spinner);
        ancientOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ancientOneSpinner.setAdapter(ancientOneAdapter);
        ancientOneSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void initAncientOneSpinner(List<String> ancientOneNameList) {
        ancientOneAdapter.clear();
        ancientOneAdapter.addAll(ancientOneNameList);
        ancientOneAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(@NotNull MaterialSpinner materialSpinner, @Nullable View view, int i, long l) {
        if (!skipCheckedChangedSpinner) {
            Timber.d(String.valueOf(ancientOneSpinner.getSelection()));
            Timber.d(String.valueOf(skipCheckedChangedSpinner));
            forgottenEndingsPresenter.onAncientOneSelected(ancientOneSpinner.getSelection());
        }
        skipCheckedChangedSpinner = false;
        invalidateView();
    }

    @Override
    public void onNothingSelected(@NotNull MaterialSpinner materialSpinner) {

    }

    @Override
    public void setItemSelected(int position) {
        ancientOneSpinner.setSelection(position);
        Timber.d(String.valueOf(position));
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.forgotten_endings_result_switch) {
            Timber.d("Article checked %b", b);
            forgottenEndingsPresenter.onResultCheckedChanged(b);
        } else
            forgottenEndingsPresenter.onConditionCheckedChanged((String) compoundButton.getText(), b);
        Timber.d("OnChecked id %s, value %b, text %s", compoundButton.getId(), b, compoundButton.getText());
    }

    public void onClick() {
        forgottenEndingsPresenter.onReadTextBtnClick();
    }

    @Override
    public void showText(String header, String text) {
        headerTV.setText(header);
        textTV.setText(text);
        headerTV.setVisibility(View.VISIBLE);
        textTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideText() {
        headerTV.setText("");
        textTV.setText("");
        headerTV.setVisibility(View.GONE);
        textTV.setVisibility(View.GONE);
    }

    @Override
    public void setAncientOneSpinnerPosition(int position) {
        Timber.d(String.valueOf(position));
        skipCheckedChangedSpinner = true;
        ancientOneSpinner.setSelection(position);
    }

    @Override
    public void setAncientOneImage(AncientOne ancientOne) {
        imageAncientOne.setImageResource(getResources().getIdentifier(ancientOne.getImageResource(), "drawable", getPackageName()));
    }

    @Override
    public void setResultSwitchVisibility(boolean visible) {
        if (visible) resultSwitch.setVisibility(View.VISIBLE);
        else resultSwitch.setVisibility(View.GONE);
    }

    @Override
    public void setResultSwitchText(boolean victory) {
        if (victory) resultSwitch.setText(R.string.win_header);
        else resultSwitch.setText(R.string.defeat_header);
    }

    @Override
    public void createConditionSwitch(Map<String, Boolean> map) {
        //conditionsContainer.removeAllViews();
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            conditionsContainer.addView(createSwitch(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void clearConditionsContainer() {
        conditionsContainer.removeAllViews();
    }

    private Switch createSwitch(String text, boolean value) {
        Switch sw = new Switch(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 16, 0, 16);
        sw.setLayoutParams(layoutParams);
        sw.setText(text);
        sw.setChecked(value);
        sw.setId(View.generateViewId());
        sw.setOnCheckedChangeListener(this);
        return sw;
    }

    @Override
    public void invalidateView() {
        scrollView.invalidate();
        scrollView.requestLayout();
    }
}
