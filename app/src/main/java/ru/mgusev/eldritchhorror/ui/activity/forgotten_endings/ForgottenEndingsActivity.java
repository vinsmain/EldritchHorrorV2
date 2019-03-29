package ru.mgusev.eldritchhorror.ui.activity.forgotten_endings;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import ir.hamsaa.RtlMaterialSpinner;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.forgotten_endings.ForgottenEndingsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.forgotten_endings.ForgottenEndingsView;
import timber.log.Timber;

public class ForgottenEndingsActivity extends MvpAppCompatActivity implements ForgottenEndingsView, CompoundButton.OnCheckedChangeListener {

    @InjectPresenter
    ForgottenEndingsPresenter forgottenEndingsPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.forgotten_endings_ancient_one_spinner)
    RtlMaterialSpinner ancientOneSpinner;
    @BindView(R.id.forgotten_endings_result_switch)
    Switch resultSwitch;
    @BindView(R.id.forgotten_endings_switch_container)
    LinearLayout conditionsContainer;
    @BindView(R.id.forgotten_endings_read_text_btn)
    Button readTextBtn;
    @BindView(R.id.forgotten_endings_header_tv)
    TextView headerTV;
    @BindView(R.id.forgotten_endings_text_tv)
    TextView textTV;

    private ArrayAdapter<String> ancientOneAdapter;
    private boolean skipCheckedChangedSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_endings);
        ButterKnife.bind(this);

        initToolbar();
        initAncientOneSpinner();
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
    }

    @Override
    public void initAncientOneSpinner(List<String> ancientOneNameList) {
        ancientOneAdapter.clear();
        ancientOneAdapter.addAll(ancientOneNameList);
        ancientOneAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAncientOneSpinnerError(String text, int time) {
        try {
            new Handler().postDelayed(() -> ancientOneSpinner.setError(text), time);
        } catch (IllegalArgumentException e) {
            Timber.d(e);
        }
    }

    @OnItemSelected({R.id.forgotten_endings_ancient_one_spinner})
    public void onItemSelected() {
        if (!skipCheckedChangedSpinner) {
            Timber.d((String) ancientOneSpinner.getItemAtPosition(ancientOneSpinner.getSelectedItemPosition() - 1));
            Timber.d(String.valueOf(ancientOneSpinner.getSelectedItemPosition() - 1));
            Timber.d(String.valueOf(skipCheckedChangedSpinner));
            forgottenEndingsPresenter.onAncientOneSelected(ancientOneSpinner.getSelectedItemPosition() - 1);
        }
        skipCheckedChangedSpinner = false;
    }

    @OnCheckedChanged({R.id.forgotten_endings_result_switch})
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.forgotten_endings_result_switch) {
            Timber.d("Article checked %b", b);
            forgottenEndingsPresenter.onResultCheckedChanged(b);
        } else
            forgottenEndingsPresenter.onConditionCheckedChanged((String) compoundButton.getText(), b);
        Timber.d("OnChecked id %s, value %b, text %s", compoundButton.getId(), b, compoundButton.getText());
    }

    @OnClick({R.id.forgotten_endings_read_text_btn})
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
}