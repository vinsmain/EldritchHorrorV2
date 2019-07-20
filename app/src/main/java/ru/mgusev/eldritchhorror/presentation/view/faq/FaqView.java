package ru.mgusev.eldritchhorror.presentation.view.faq;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.strategy.AddToEndSingleByTagStateStrategy;

public interface FaqView extends MvpView {

    String ROTATE_ICON_TAG = "rotate icon";

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = ROTATE_ICON_TAG)
    void startRefreshIconRotate();

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = ROTATE_ICON_TAG)
    void stopRefreshIconRotate();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDataToAdapter(List<Article> articleList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setTextToSearchView(String text);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setExpandSearchView(boolean isExpanded);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showAlert(int messageResId);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setNoneResultsTVVisibility(boolean isVisible);
}
