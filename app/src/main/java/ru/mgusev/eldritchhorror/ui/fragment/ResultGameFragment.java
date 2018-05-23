package ru.mgusev.eldritchhorror.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ResultGamePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.ResultGameView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class ResultGameFragment extends MvpAppCompatFragment implements ResultGameView, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, TextWatcher {

    @InjectPresenter
    ResultGamePresenter resultGamePresenter;

    private Switch resultSwitch;
    private Switch defeatByEliminationSwitch;
    private Switch defeatByMythosDepletionSwitch;
    private Switch defeatByAwakenedAncientOneSwitch;
    private TableLayout victoryTable;
    private TableLayout defeatTable;
    private RadioGroup mysteriesRadioGroup;
    private EditText gatesCountTV;
    private EditText monstersCountTV;
    private EditText curseCountTV;
    private EditText rumorsCountTV;
    private EditText cluesCountTV;
    private EditText blessedCountTV;
    private EditText doomCountTV;

    private Switch[] switchArray;
    private int[] idArray;

    public static ResultGameFragment newInstance(int page) {
        ResultGameFragment pageFragment = new ResultGameFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_game, null);
        initView(view);
        initListeners();
        switchArray = new Switch[]{defeatByEliminationSwitch, defeatByMythosDepletionSwitch, defeatByAwakenedAncientOneSwitch};
        idArray = new int[]{R.id.result_game_defeat_by_elimination_switch, R.id.result_game_defeat_by_mythos_deplition_switch, R.id.result_game_defeat_by_awakened_ancient_one_switch};
        return view;
    }

    private void initView(View view) {
        resultSwitch = view.findViewById(R.id.result_game_result_switch);
        defeatByEliminationSwitch = view.findViewById(R.id.result_game_defeat_by_elimination_switch);
        defeatByMythosDepletionSwitch = view.findViewById(R.id.result_game_defeat_by_mythos_deplition_switch);
        defeatByAwakenedAncientOneSwitch = view.findViewById(R.id.result_game_defeat_by_awakened_ancient_one_switch);
        victoryTable = view.findViewById(R.id.result_game_victory_table);
        defeatTable = view.findViewById(R.id.result_game_defeat_table);
        mysteriesRadioGroup = view.findViewById(R.id.result_game_mysteries_radio_group);
        gatesCountTV = view.findViewById(R.id.result_game_gates_count);
        monstersCountTV = view.findViewById(R.id.result_game_monsters_count);
        curseCountTV = view.findViewById(R.id.result_game_curse_count);
        rumorsCountTV = view.findViewById(R.id.result_game_rumors_count);
        cluesCountTV = view.findViewById(R.id.result_game_clues_count);
        blessedCountTV = view.findViewById(R.id.result_game_blessed_count);
        doomCountTV = view.findViewById(R.id.result_game_doom_count);
    }

    private void initListeners() {
        resultSwitch.setOnCheckedChangeListener(this);
        defeatByEliminationSwitch.setOnCheckedChangeListener(this);
        defeatByMythosDepletionSwitch.setOnCheckedChangeListener(this);
        defeatByAwakenedAncientOneSwitch.setOnCheckedChangeListener(this);
        mysteriesRadioGroup.setOnCheckedChangeListener(this);
        gatesCountTV.addTextChangedListener(this);
        monstersCountTV.addTextChangedListener(this);
        curseCountTV.addTextChangedListener(this);
        rumorsCountTV.addTextChangedListener(this);
        cluesCountTV.addTextChangedListener(this);
        blessedCountTV.addTextChangedListener(this);
        doomCountTV.addTextChangedListener(this);
    }

    @Override
    public void setResultSwitchChecked(boolean value) {
        resultSwitch.setChecked(value);
    }

    @Override
    public void showVictoryTable() {
        victoryTable.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVictoryTable() {
        victoryTable.setVisibility(View.GONE);
    }

    @Override
    public void showDefeatTable() {
        defeatTable.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDefeatTable() {
        defeatTable.setVisibility(View.GONE);
    }

    @Override
    public void setVictorySwitchText() {
        resultSwitch.setText(R.string.win_header);
    }

    @Override
    public void setDefeatSwitchText() {
        resultSwitch.setText(R.string.defeat_header);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.result_game_result_switch:
                resultGamePresenter.setResultSwitch(b);
                break;
            default:
                if (b) setDefeatReasonSwitchesChecked(compoundButton.getId());
                break;
        }
    }

    private void setDefeatReasonSwitchesChecked(int currentId) {
        for (int i = 0; i < idArray.length; i++) {
            if (idArray[i] == currentId) switchArray[i].setClickable(false);
            else {
                switchArray[i].setClickable(true);
                switchArray[i].setChecked(false);
            }
        }
        resultGamePresenter.setDefeatReasons(defeatByEliminationSwitch.isChecked(), defeatByMythosDepletionSwitch.isChecked(), defeatByAwakenedAncientOneSwitch.isChecked());
    }

    @Override
    public void showMysteriesRadioGroup(AncientOne ancientOne) {
        for (int i = 0; i < mysteriesRadioGroup.getChildCount(); i++) {
            if (i <= ancientOne.getMaxMysteries()) mysteriesRadioGroup.getChildAt(i).setVisibility(View.VISIBLE);
            else {
                if (((RadioButton)mysteriesRadioGroup.getChildAt(i)).isChecked()) ((RadioButton)mysteriesRadioGroup.getChildAt(0)).setChecked(true);
                mysteriesRadioGroup.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setDefeatReasonSwitchChecked(boolean v1, boolean v2, boolean v3) {
        defeatByEliminationSwitch.setChecked(v1);
        defeatByMythosDepletionSwitch.setChecked(v2);
        defeatByAwakenedAncientOneSwitch.setChecked(v3);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        System.out.println(radioGroup.getFocusedChild());
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.result_game_result_switch:
                //resultGamePresenter.setResultSwitchText(b);
                break;
            default:
                break;
        }
    }

    @Override
    public void setMysteryValue(int i) {
        ((RadioButton)mysteriesRadioGroup.getChildAt(i)).setChecked(true);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        resultGamePresenter.setResult(getResultFromField(gatesCountTV), getResultFromField(monstersCountTV), getResultFromField(curseCountTV), getResultFromField(rumorsCountTV),
                getResultFromField(cluesCountTV), getResultFromField(blessedCountTV), getResultFromField(doomCountTV));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private int getResultFromField(EditText editText) {
        if (editText.getText().toString().equals("")) return 0;
        else return Integer.parseInt(editText.getText().toString());
    }
}