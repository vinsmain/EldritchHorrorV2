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
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

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
        return view;
    }

    private void initView(View view) {
        resultSwitch = view.findViewById(R.id.result_game_result_switch);
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
    public void setResultSwitchText(boolean value) {
        if (value) resultSwitch.setText(R.string.win_header);
        else resultSwitch.setText(R.string.defeat_header);
    }

    @Override
    public void setResultSwitchChecked(boolean value) {
        resultSwitch.setChecked(value);
        showResultTable(value);
    }

    @Override
    public void showResultTable(boolean value) {
        if (value) {
            victoryTable.setVisibility(View.VISIBLE);
            defeatTable.setVisibility(View.GONE);
        } else {
            victoryTable.setVisibility(View.GONE);
            defeatTable.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.result_game_result_switch:
                resultGamePresenter.setResultSwitchText(b);
                break;
            default:
                break;
        }
    }

    @Override
    public void showMysteriesRadioGroup(AncientOne ancientOne) {
        for (int i = 0; i < mysteriesRadioGroup.getChildCount(); i++) {
            if (i <= ancientOne.getMaxMysteries()) mysteriesRadioGroup.getChildAt(i).setVisibility(View.VISIBLE);
            else mysteriesRadioGroup.getChildAt(i).setVisibility(View.GONE);
        }
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