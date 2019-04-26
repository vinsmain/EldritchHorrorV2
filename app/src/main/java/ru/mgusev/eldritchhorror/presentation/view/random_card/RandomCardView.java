package ru.mgusev.eldritchhorror.presentation.view.random_card;

import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

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
    void showAlertIfOtherCardNone();
}
