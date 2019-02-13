package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.AncientOne;
import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface ResultGameView extends MvpView {

    String TIME_PICKER_DIALOG_TAG = "timePickerDialog";

    @StateStrategyType(SkipStrategy.class)
    void setResultSwitchChecked(boolean value);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showVictoryTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideVictoryTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDefeatTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideDefeatTable();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setVictorySwitchText();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDefeatSwitchText();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMysteriesRadioGroup(AncientOne ancientOne);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void initRumorSpinner(List<String> rumorList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setRumorSpinnerPosition(int rumorPosition);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setResultValues(int gatesCount, int monstersCount, int curseCount, int rumorsCount, int cluesCount, int blessedCount, int doomCount);

    @StateStrategyType(SkipStrategy.class)
    void setMysteryValue(int i);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void setDefeatReasonSwitchChecked(boolean v1, boolean v2, boolean v3, boolean v4, boolean v5);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setVisibilityRumorSpinnerTR(boolean visible);

    @StateStrategyType(SkipStrategy.class)
    void setCommentValue(String text);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = TIME_PICKER_DIALOG_TAG)
    void showTimePickerDialog(int time);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setTimeToField(String time);

    @StateStrategyType(value = DismissDialogStrategy.class, tag = TIME_PICKER_DIALOG_TAG)
    void dismissTimePicker();
}