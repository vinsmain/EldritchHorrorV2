package ru.mgusev.eldritchhorror.presentation.view.random_card;

import android.net.Uri;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.model.Card;
import ru.mgusev.eldritchhorror.strategy.DismissDialogStrategy;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface RandomCardView extends MvpView {

    String LOG_DIALOG_TAG = "logDialog";

    void loadImage(Uri source);

    void setTitle(String text);

    void setCategory(String text);

    void setType(String text);

    @StateStrategyType(value = AddToEndSingleStrategy.class, tag = LOG_DIALOG_TAG)
    void showLogDialog(List<Card> cardList);

    @StateStrategyType(value = DismissDialogStrategy.class, tag = LOG_DIALOG_TAG)
    void hideLogDialog();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showAlertIfOtherCardNone(boolean v);
}
