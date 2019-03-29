package ru.mgusev.eldritchhorror.presentation.presenter.faq;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.HttpException;
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
        List<Article> articleList = new ArrayList<>(getArticleListWithHeaders(getSearchResult(text)));
        getViewState().setDataToAdapter(articleList);
        getViewState().setNoneResultsTVVisibility(articleList.isEmpty());
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

    private List<Article> getArticleListWithHeaders(List<Article> list) {
        List<Article> listWithHeaders = new ArrayList<>();
        String tagTitle = "";
        int headerId = -1;
        for (Article article : list) {
            if (!article.getTags().getItemTags().isEmpty()) {
                if (!article.getTags().getItemTags().get(0).getTitle().equals(tagTitle)) {
                    Article header = new Article();
                    tagTitle = article.getTags().getItemTags().get(0).getTitle();
                    header.setId(headerId);
                    header.setTitle("#errata");
                    header.setIntrotext("<h2>" + tagTitle + "</h2>");
                    listWithHeaders.add(header);
                    headerId --;
                }
            }
            listWithHeaders.add(article);
        }
        return listWithHeaders;
    }

    public void showSuccessAlert() {
        getViewState().showAlert(R.string.update_complete);
    }

    public void showErrorAlert(Throwable e) {
        Timber.d(e);
        if (e instanceof UnknownHostException || e instanceof HttpException)
            getViewState().showAlert(R.string.connection_error_header);
        else
            getViewState().showAlert(R.string.format_data_error_header);
    }
}