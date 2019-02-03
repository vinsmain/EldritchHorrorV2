package ru.mgusev.eldritchhorror.ui.activity.forgotten_endings;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;
import ir.hamsaa.RtlMaterialSpinner;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.presentation.presenter.forgotten_endings.ForgottenEndingsPresenter;
import ru.mgusev.eldritchhorror.presentation.view.forgotten_endings.ForgottenEndingsView;
import timber.log.Timber;

public class ForgottenEndingsActivity extends MvpAppCompatActivity implements ForgottenEndingsView {

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

    private List<Switch> conditionSwitchList;
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
        switch (compoundButton.getId()) {
            case R.id.forgotten_endings_result_switch:
                forgottenEndingsPresenter.onResultCheckedChanged(b);
                break;
//            case R.id.forgotten_endings_awakening_switch:
//                if (!b && !easyMythosSwitch.isChecked() && !hardMythosSwitch.isChecked())
//                    easyMythosSwitch.setChecked(true);
//                break;
//            case R.id.start_data_hard_mythos:
//                if (!b && !easyMythosSwitch.isChecked() && !normalMythosSwitch.isChecked())
//                    easyMythosSwitch.setChecked(true);
//                break;
            default:
                break;
        }
    }

    @Override
    public void setAncientOneSpinnerPosition(int position) {
        Timber.d(String.valueOf(position));
        skipCheckedChangedSpinner = true;
        ancientOneSpinner.setSelection(position);
    }

    @Override
    public void setResultSwitchText(boolean victory) {
        if (victory) resultSwitch.setText(R.string.win_header);
        else resultSwitch.setText(R.string.defeat_header);
    }

    @Override
    public void setVisibilityAwakeningSwitch(boolean visible) {
//        if (visible) awakeningSwitch.setVisibility(View.VISIBLE);
//        else awakeningSwitch.setVisibility(View.GONE);
    }

    @Override
    public void createConditionSwitch(List<String> conditionList) {
        conditionsContainer.removeAllViews();
        conditionSwitchList = new ArrayList<>();
        for (String condition : conditionList) {
            Timber.d("Условие: %s", condition);
            if (conditionsContainer != null) {
                Switch sw = createSwitch(condition);
                conditionsContainer.addView(sw);
                conditionSwitchList.add(sw);
            }
        }
        forgottenEndingsPresenter.setConditionSwitchList(conditionSwitchList);
    }

    @Override
    public void addConditionSwitch(List<Switch> list) {
        for (Switch sw : list) {
            if (conditionsContainer != null) {
                conditionsContainer.addView(sw);
            }
        }
    }

    //C:\Users\vinsm\AppData\Local\Android\Sdk\platform-tools>adb tcpip 5555
    //restarting in TCP mode port: 5555
//    C:\Users\vinsm\AppData\Local\Android\Sdk\platform-tools>adb connect 192.168.211.52
//    connected to 192.168.211.52:5555

    @Override
    public void clearConditionsContainer() {
        conditionsContainer.removeAllViews();
    }

    private Switch createSwitch(String text) {
        Switch sw = new Switch(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //layoutParams.setMargins(30, 30, 30, 30);
        sw.setLayoutParams(layoutParams);
        sw.setText(text);
        sw.setId(View.generateViewId());
        return sw;
    }
}