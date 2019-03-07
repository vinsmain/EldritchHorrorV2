package ru.mgusev.eldritchhorror.presentation.view.faq;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.mgusev.eldritchhorror.api.json_model.Article;

public interface FaqView extends MvpView {
    void setDataToAdapter(List<Article> articleList);
}
