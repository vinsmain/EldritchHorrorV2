package ru.mgusev.eldritchhorror.ui.fragment.pager;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TimePicker;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.ResultGamePresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.ResultGameView;
import ru.mgusev.eldritchhorror.support.FixedTextInputEditText;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class ResultGameFragment extends MvpAppCompatFragment implements ResultGameView, TimePickerDialog.OnTimeSetListener {

    @InjectPresenter
    ResultGamePresenter resultGamePresenter;

    @BindView(R.id.result_game_result_switch) Switch resultSwitch;
    @BindView(R.id.result_game_mysteries_radio_group) RadioGroup mysteriesRadioGroup;
    @BindView(R.id.result_game_defeat_by_elimination_switch) Switch defeatByEliminationSwitch;
    @BindView(R.id.result_game_defeat_by_mythos_deplition_switch) Switch defeatByMythosDepletionSwitch;
    @BindView(R.id.result_game_defeat_by_awakened_ancient_one_switch) Switch defeatByAwakenedAncientOneSwitch;
    @BindView(R.id.result_game_defeat_by_surrender_switch) Switch defeatBySurrenderSwitch;
    @BindView(R.id.result_game_defeat_by_rumor_switch) Switch defeatByRumorSwitch;
    @BindView(R.id.result_game_defeat_table) TableLayout defeatTable;
    @BindView(R.id.result_game_gates_count) EditText gatesCountTV;
    @BindView(R.id.result_game_monsters_count) EditText monstersCountTV;
    @BindView(R.id.result_game_curse_count) EditText curseCountTV;
    @BindView(R.id.result_game_rumors_count) EditText rumorsCountTV;
    @BindView(R.id.result_game_clues_count) EditText cluesCountTV;
    @BindView(R.id.result_game_blessed_count) EditText blessedCountTV;
    @BindView(R.id.result_game_doom_count) EditText doomCountTV;
    @BindView(R.id.result_game_victory_table) TableLayout victoryTable;
    @BindView(R.id.result_game_comment_tiet) FixedTextInputEditText commentTIED;
    @BindView(R.id.result_game_defeat_rumor_spinner) Spinner rumorSpinner;
    @BindView(R.id.result_game_defeat_rumor_spinner_row) TableRow rumorSpinnerTR;

    private static final int EDIT_TEXT_INDEX = 1; //Индекс EditText в строке TableRow
    private Unbinder unbinder;
    private Switch[] switchArray;
    private int[] idArray;
    private ArrayAdapter<String> rumorAdapter;

    private TimePickerDialog timePickerDialog;
    private int DIALOG_TIME = 1;
    private int hour = 14;
    private int minute = 35;

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

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(getContext(), MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

        View view = inflater.inflate(R.layout.fragment_result_game, null);
        unbinder = ButterKnife.bind(this, view);
        rumorAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner);
        rumorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rumorSpinner.setAdapter(rumorAdapter);

        switchArray = new Switch[]{defeatByEliminationSwitch, defeatByMythosDepletionSwitch, defeatByAwakenedAncientOneSwitch, defeatBySurrenderSwitch, defeatByRumorSwitch};
        idArray = new int[]{R.id.result_game_defeat_by_elimination_switch, R.id.result_game_defeat_by_mythos_deplition_switch, R.id.result_game_defeat_by_awakened_ancient_one_switch, R.id.result_game_defeat_by_surrender_switch, R.id.result_game_defeat_by_rumor_switch};
        return view;
    }

    @Override
    public void initRumorSpinner(List<String> rumorList) {
        rumorAdapter.clear();
        rumorAdapter.addAll(rumorList);
        rumorAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRumorSpinnerPosition(int rumorPosition) {
        rumorSpinner.setSelection(rumorPosition, false);
    }

    public void setResultValues(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount) {
        if (gatesCount != 0) gatesCountTV.setText(String.valueOf(gatesCount));
        if (monstersCount != 0) monstersCountTV.setText(String.valueOf(monstersCount));
        if (curseCount != 0) curseCountTV.setText(String.valueOf(curseCount));
        if (rumorsCount != 0) rumorsCountTV.setText(String.valueOf(rumorsCount));
        if (cluesCount != 0) cluesCountTV.setText(String.valueOf(cluesCount));
        if (blessedCount != 0) blessedCountTV.setText(String.valueOf(blessedCount));
        if (doomCount != 0) doomCountTV.setText(String.valueOf(doomCount));
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

    @OnCheckedChanged({R.id.result_game_result_switch, R.id.result_game_defeat_by_elimination_switch, R.id.result_game_defeat_by_mythos_deplition_switch,
            R.id.result_game_defeat_by_awakened_ancient_one_switch, R.id.result_game_defeat_by_surrender_switch, R.id.result_game_defeat_by_rumor_switch})
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
        resultGamePresenter.setDefeatReasons(defeatByEliminationSwitch.isChecked(), defeatByMythosDepletionSwitch.isChecked(), defeatByAwakenedAncientOneSwitch.isChecked(), defeatByRumorSwitch.isChecked(), defeatBySurrenderSwitch.isChecked());
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

    @OnCheckedChanged({R.id.result_game_mystery_0, R.id.result_game_mystery_1, R.id.result_game_mystery_2, R.id.result_game_mystery_3, R.id.result_game_mystery_4, R.id.result_game_mystery_5})
    public void onCheckedChangedMysteries(CompoundButton compoundButton, boolean b) {
        if (b) resultGamePresenter.setSolvedMysteriesCount(Integer.parseInt((String) compoundButton.getText()));
    }

    @Override
    public void setDefeatReasonSwitchChecked(boolean v1, boolean v2, boolean v3, boolean v4, boolean v5) {
        defeatByEliminationSwitch.setChecked(v1);
        defeatByMythosDepletionSwitch.setChecked(v2);
        defeatByAwakenedAncientOneSwitch.setChecked(v3);
        defeatByRumorSwitch.setChecked(v4);
        defeatBySurrenderSwitch.setChecked(v5);
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

    @OnTextChanged(R.id.result_game_comment_tiet)
    public void onCommentTextChanged() {
        resultGamePresenter.setComment(String.valueOf(commentTIED.getText()));
    }

    @OnItemSelected(R.id.result_game_defeat_rumor_spinner)
    public void onItemSelected() {
        resultGamePresenter.setCurrentRumor((String) rumorSpinner.getSelectedItem());
        resultGamePresenter.setRumorSpinnerPosition();
    }

    @Override
    public void setVisibilityRumorSpinnerTR(boolean visible) {
        rumorSpinnerTR.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.result_game_defeat_rumor_spinner_row)
    public void onRumorRowClick() {
        rumorSpinner.performClick();
    }

    @OnClick({R.id.gatesCountRow, R.id.monstersCountRow, R.id.curseCountRow, R.id.rumorsCountRow, R.id.cluesCountRow, R.id.blessedCountRow, R.id.doomCountRow})
    public void inClick(View view) {
        showSoftKeyboardOnView(view);
    }

    @OnClick({R.id.result_game_time_row})
    public void onTimeRowClick() {
        resultGamePresenter.onTimeRowClick();
    }

    private void showSoftKeyboardOnView(View view) {
        EditText editText = (EditText) ((ViewGroup)view).getChildAt(EDIT_TEXT_INDEX);
        (new Handler()).postDelayed(() -> {
            // Yes, I know what you are thinking about that. If you knew something better by any chance it would be magnificent to have your idea here in code.
            editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            editText.setSelection(editText.length());
        }, 200);
    }

    private int getResultFromField(EditText editText) {
        if (editText.getText().toString().equals("")) return 0;
        else return Integer.parseInt(editText.getText().toString());
    }

    @Override
    public void setCommentValue(String text) {
        commentTIED.setText(text);
    }

    @Override
    public void showTimePickerDialog(int hour, int minute) {
        //Calendar currentDate;
//        if (startDataPresenter.getTempDate() != null) currentDate = startDataPresenter.getTempDate();
//        else currentDate = date;
        timePickerDialog = new TimePickerDialog(getContext(), this, hour, minute, true);
        //timePickerDialog.setOnCancelListener(this);
        timePickerDialog.setCancelable(false);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hour = i;
        minute = i1;
        //tvTime.setText("Time is " + hour + " hours " + ResultGameFragment.this.minute + " minutes");
        Timber.d("Time is " + hour + " hours " + ResultGameFragment.this.minute + " minutes");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}