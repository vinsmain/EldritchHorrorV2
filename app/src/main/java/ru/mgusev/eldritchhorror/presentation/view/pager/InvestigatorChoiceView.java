package ru.mgusev.eldritchhorror.presentation.view.pager;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.mgusev.eldritchhorror.model.Investigator;
import ru.mgusev.eldritchhorror.support.DismissDialogStrategy;

public interface InvestigatorChoiceView extends MvpView {

    String DIALOG_TAG = "dialog";

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
    void showDialog();

    @StateStrategyType(value = DismissDialogStrategy.class, tag = DIALOG_TAG)
    void hideDialog();

    @StateStrategyType(SkipStrategy.class)
    void showError();
}
