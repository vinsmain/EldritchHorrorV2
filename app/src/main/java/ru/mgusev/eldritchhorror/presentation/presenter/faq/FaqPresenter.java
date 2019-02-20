package ru.mgusev.eldritchhorror.presentation.presenter.faq;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.api.FaqAPIService;
import ru.mgusev.eldritchhorror.api.model.article.Article;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.faq.FaqView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class FaqPresenter extends MvpPresenter<FaqView> {

    @Inject
    Repository repository;
    @Inject
    FaqAPIService service;

    public FaqPresenter() {
        App.getComponent().inject(this);

        service.setPresenter(this);

        service.checkUpdate();
        service.getArticles();
    }

    public void updateArticleList(Article article) {
        getViewState().setDataToAdapter(article.getData().getData().getResults());
    }
}
