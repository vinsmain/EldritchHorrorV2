package ru.mgusev.eldritchhorror.presentation.view.pager;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

public interface InvestigatorChoiceView extends MvpView {

    String DIALOG_TAG = "clearInvListDialog";

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateAllItems(List<Investigator> investigatorList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateItem(int position, List<Investigator> investigatorList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void moveItem(int oldPosition, int newPosition, List<Investigator> investigatorList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void removeItem(int position, List<Investigator> investigatorList);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showInvestigatorActivity();

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = DIALOG_TAG)
    void showClearInvListDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = DIALOG_TAG)
    void hideClearInvListDialog();

    @StateStrategyType(SkipStrategy.class)
    void showError();
}
