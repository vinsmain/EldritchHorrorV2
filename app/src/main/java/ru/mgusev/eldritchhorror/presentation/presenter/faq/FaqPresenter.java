package ru.mgusev.eldritchhorror.presentation.presenter.faq;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.api.FaqAPIService;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.api.json_model.Response;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.faq.FaqView;
import ru.mgusev.eldritchhorror.repository.Repository;

@InjectViewState
public class FaqPresenter extends MvpPresenter<FaqView> {

    @Inject
    Repository repository;
    @Inject
    FaqAPIService service;
    private List<Article> articleList;

    public FaqPresenter() {
        App.getComponent().inject(this);

        service.setPresenter(this);

        service.getArticles();
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public void updateArticleList(Response response) {
        getViewState().setDataToAdapter(response.getData().getData().getArticles());
    }

    public void clickSearch(String s) {

    }
}
