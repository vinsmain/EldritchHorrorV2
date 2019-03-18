package ru.mgusev.eldritchhorror.presentation.presenter.faq;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.api.FaqAPIService;
import ru.mgusev.eldritchhorror.api.json_model.Article;
import ru.mgusev.eldritchhorror.app.App;
import ru.mgusev.eldritchhorror.presentation.view.faq.FaqView;
import ru.mgusev.eldritchhorror.repository.Repository;
import timber.log.Timber;

@InjectViewState
public class FaqPresenter extends MvpPresenter<FaqView> {

    @Inject
    Repository repository;
    @Inject
    FaqAPIService service;
    private String searchText = "";

    public FaqPresenter() {
        App.getComponent().inject(this);
        service.setPresenter(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        if (repository.getArticleList().isEmpty())
            clickRefresh();
        else {
            clickSearch(getSearchText());
            completeUpdateArticleList();
        }
    }

    public void setCurrentArticleList(List<Article> articleList) {
        //articleList = replaceTestIcon(articleList);
        Collections.sort(articleList);
        repository.setArticleList(articleList);
    }

    public void clickRefresh() {
        getViewState().startRefreshIconRotate();
        service.getArticles();
    }

    public void completeUpdateArticleList() {
        getViewState().stopRefreshIconRotate();
    }

    public void clickSearch(String text) {
        searchText = text;
        getViewState().setDataToAdapter(getSearchResult(text));
    }

    public String getSearchText() {
        return searchText;
    }

    public void changeSearchText(String text) {
        getViewState().setTextToSearchView(text);
    }

    public void changeExpandedSearchView(boolean isExpanded) {
        getViewState().setExpandSearchView(isExpanded);
    }

    private List<Article> getSearchResult(String text) {
        List<Article> resultList = new ArrayList<>();
        for (Article article : repository.getArticleList()) {
            if (article.getTitle().toLowerCase().contains(text.toLowerCase()) || article.getIntrotext().toLowerCase().contains(text.toLowerCase()))
                resultList.add(article);
        }
        return resultList;
    }

    public void showError(Throwable e) {
        Timber.d(e);
        if (e instanceof UnknownHostException)
            getViewState().showError(R.string.connection_error_header);
        else
            getViewState().showError(R.string.format_data_error_header);
    }
}