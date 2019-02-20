package ru.mgusev.eldritchhorror.presentation.view.faq;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.mgusev.eldritchhorror.api.model.article.Result;

public interface FaqView extends MvpView {
    void setDataToAdapter(List<Result> articleList);
}
