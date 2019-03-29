package ru.mgusev.eldritchhorror.api;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.mgusev.eldritchhorror.api.json_model.Response;
import ru.mgusev.eldritchhorror.model.Localization;
import ru.mgusev.eldritchhorror.presentation.presenter.faq.FaqPresenter;
import timber.log.Timber;

public class FaqAPIService {

    private final static String OPTION = "com_api";
    private final static String APP = "articles";
    private final static String RESOURCE = "article";
    private final static long CATEGORY_ID_RU = 9;
    private final static long CATEGORY_ID_EN = 10;
    private final static long LIMIT = 1000;
    private final static String API_KEY = "526b11779725fcceef927b69f2035ed7";

    private Context context;
    private FaqPresenter presenter;
    private long categoryId;
    private APIService service;

    public FaqAPIService(Context context) {
        this.context = context;
        service = FaqAPI.getClient(context).create(APIService.class);
    }

    public void setPresenter(FaqPresenter presenter) {
        this.presenter = presenter;
    }

    private void initCategoryId() {
        if (Localization.getInstance().isRusLocale())
            categoryId = CATEGORY_ID_RU;
        else
            categoryId = CATEGORY_ID_EN;
    }

    public void getArticles() {
        initCategoryId();
        service.getArticles(OPTION, APP, RESOURCE, categoryId, LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response response) {
                        presenter.setCurrentArticleList(response.getResponseData().getArticleData().getArticles());
                        Timber.d(String.valueOf(response.getResponseData().getArticleData().getArticles().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.showErrorAlert(e);
                        presenter.completeUpdateArticleList();
                        Timber.d("On Error");
                        Timber.d(e);
                    }

                    @Override
                    public void onComplete() {
                        presenter.clickSearch(presenter.getSearchText());
                        presenter.completeUpdateArticleList();
                        presenter.showSuccessAlert();
                        Timber.d("On Complete");
                    }
                });
    }
}