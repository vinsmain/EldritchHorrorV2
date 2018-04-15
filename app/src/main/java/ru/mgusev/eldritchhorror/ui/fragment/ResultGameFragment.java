package ru.mgusev.eldritchhorror.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

public class ResultGameFragment extends MvpAppCompatFragment implements ResultGameView, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    @InjectPresenter
    ResultGamePresenter resultGamePresenter;

    private Switch resultSwitch;
    private TableLayout victoryTable;
    private TableLayout defeatTable;
    private RadioGroup mysteriesRadioGroup;

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
        resultSwitch = view.findViewById(R.id.result_game_result_switch);
        resultSwitch.setOnCheckedChangeListener(this);

        victoryTable = view.findViewById(R.id.result_game_victory_table);
        defeatTable = view.findViewById(R.id.result_game_defeat_table);

        mysteriesRadioGroup = view.findViewById(R.id.result_game_mysteries_radio_group);
        mysteriesRadioGroup.setOnCheckedChangeListener(this);

        return view;
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
}