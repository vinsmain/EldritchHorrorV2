package ru.mgusev.eldritchhorror.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ResultGamePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.ResultGameView;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class ResultGameFragment extends MvpAppCompatFragment implements ResultGameView {

    @InjectPresenter
    ResultGamePresenter resultGamePresenter;

    @BindView(R.id.activityResultHeader)     TextView activityResultHeader;
    @BindView(R.id.result_game_result_switch) Switch resultSwitch;
    @BindView(R.id.mysteriesCountHeader)     TextView mysteriesCountHeader;
    @BindView(R.id.result_game_mystery_0)     RadioButton resultGameMystery0;
    @BindView(R.id.result_game_mystery_1)     RadioButton resultGameMystery1;
    @BindView(R.id.result_game_mystery_2)     RadioButton resultGameMystery2;
    @BindView(R.id.result_game_mystery_3)     RadioButton resultGameMystery3;
    @BindView(R.id.result_game_mystery_4)     RadioButton resultGameMystery4;
    @BindView(R.id.result_game_mystery_5)     RadioButton resultGameMystery5;
    @BindView(R.id.result_game_mysteries_radio_group) RadioGroup mysteriesRadioGroup;
    @BindView(R.id.result_game_defeat_by_elimination_switch) Switch defeatByEliminationSwitch;
    @BindView(R.id.result_game_defeat_by_mythos_deplition_switch) Switch defeatByMythosDepletionSwitch;
    @BindView(R.id.result_game_defeat_by_awakened_ancient_one_switch) Switch defeatByAwakenedAncientOneSwitch;
    @BindView(R.id.result_game_defeat_table) TableLayout defeatTable;
    @BindView(R.id.gatesCountHeader)     TextView gatesCountHeader;
    @BindView(R.id.result_game_gates_count) EditText gatesCountTV;
    @BindView(R.id.gatesCountRow)     TableRow gatesCountRow;
    @BindView(R.id.monstersCountHeader)    TextView monstersCountHeader;
    @BindView(R.id.result_game_monsters_count) EditText monstersCountTV;
    @BindView(R.id.monstersCountRow)    TableRow monstersCountRow;
    @BindView(R.id.curseCountHeader)    TextView curseCountHeader;
    @BindView(R.id.result_game_curse_count) EditText curseCountTV;
    @BindView(R.id.curseCountRow)    TableRow curseCountRow;
    @BindView(R.id.rumorsCountHeader)    TextView rumorsCountHeader;
    @BindView(R.id.result_game_rumors_count) EditText rumorsCountTV;
    @BindView(R.id.rumorsCountRow)     TableRow rumorsCountRow;
    @BindView(R.id.cluesCountHeader)    TextView cluesCountHeader;
    @BindView(R.id.result_game_clues_count) EditText cluesCountTV;
    @BindView(R.id.cluesCountRow)    TableRow cluesCountRow;
    @BindView(R.id.blessedCountHeader)    TextView blessedCountHeader;
    @BindView(R.id.result_game_blessed_count) EditText blessedCountTV;
    @BindView(R.id.blessedCountRow)    TableRow blessedCountRow;
    @BindView(R.id.doomCountHeader)    TextView doomCountHeader;
    @BindView(R.id.result_game_doom_count) EditText doomCountTV;
    @BindView(R.id.doomCountRow)    TableRow doomCountRow;
    @BindView(R.id.result_game_victory_table)  TableLayout victoryTable;

    private Unbinder unbinder;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_game, null);
        unbinder = ButterKnife.bind(this, view);
        switchArray = new Switch[]{defeatByEliminationSwitch, defeatByMythosDepletionSwitch, defeatByAwakenedAncientOneSwitch};
        idArray = new int[]{R.id.result_game_defeat_by_elimination_switch, R.id.result_game_defeat_by_mythos_deplition_switch, R.id.result_game_defeat_by_awakened_ancient_one_switch};
        return view;
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

    @OnCheckedChanged({R.id.result_game_result_switch, R.id.result_game_defeat_by_elimination_switch, R.id.result_game_defeat_by_mythos_deplition_switch, R.id.result_game_defeat_by_awakened_ancient_one_switch})
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
            if (i <= ancientOne.getMaxMysteries())
                mysteriesRadioGroup.getChildAt(i).setVisibility(View.VISIBLE);
            else {
                if (((RadioButton) mysteriesRadioGroup.getChildAt(i)).isChecked())
                    ((RadioButton) mysteriesRadioGroup.getChildAt(0)).setChecked(true);
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
    public void setMysteryValue(int i) {
        ((RadioButton) mysteriesRadioGroup.getChildAt(i)).setChecked(true);
    }

    @OnTextChanged({R.id.result_game_gates_count, R.id.result_game_monsters_count, R.id.result_game_curse_count,
            R.id.result_game_rumors_count, R.id.result_game_clues_count, R.id.result_game_blessed_count, R.id.result_game_doom_count})
    public void onTextChanged() {
        resultGamePresenter.setResult(getResultFromField(gatesCountTV), getResultFromField(monstersCountTV), getResultFromField(curseCountTV), getResultFromField(rumorsCountTV),
                getResultFromField(cluesCountTV), getResultFromField(blessedCountTV), getResultFromField(doomCountTV));
    }

    @OnClick({R.id.gatesCountRow, R.id.monstersCountRow, R.id.curseCountRow, R.id.rumorsCountRow, R.id.cluesCountRow, R.id.blessedCountRow, R.id.doomCountRow})
    public void inClick(View view) {
        switch (view.getId()) {
            case R.id.gatesCountRow:
                System.out.println("EDITTEXT");
                gatesCountTV.setShowSoftInputOnFocus(true);
                break;
            default:
                //if (b) setDefeatReasonSwitchesChecked(compoundButton.getId());
                break;
        }
    }

    private int getResultFromField(EditText editText) {
        if (editText.getText().toString().equals("")) return 0;
        else return Integer.parseInt(editText.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}