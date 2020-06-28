package ru.mgusev.eldritchhorror.api;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.mgusev.eldritchhorror.api.json_model.Response;
import ru.mgusev.eldritchhorror.model.Localization;
import ru.mgusev.eldritchhorror.presentation.presenter.ancient_one_info.AncientOneInfoPresenter;
import ru.mgusev.eldritchhorror.presentation.presenter.faq.FaqPresenter;
import timber.log.Timber;

public class EHAPIService {

    private final static String OPTION = "com_api";
    private final static String APP = "articles";
    private final static String RESOURCE = "article";
    private final static long FAQ_CATEGORY_ID_RU = 9;
    private final static long FAQ_CATEGORY_ID_EN = 10;
    private final static long ANCIENT_ONE_STORY_CATEGORY_ID_RU = 11;
    private final static long ANCIENT_ONE_INFO_CATEGORY_ID_RU = 12;
    private final static long LIMIT = 1000;
    private final static String API_KEY = "526b11779725fcceef927b69f2035ed7";

    private Context context;
    private FaqPresenter faqPresenter;
    private AncientOneInfoPresenter ancientOneInfoPresenter;
    private long categoryId;
    private APIService service;

    public EHAPIService(Context context) {
        this.context = context;
        service = FaqAPI.getClient(context).create(APIService.class);
    }

    public void setPresenter(FaqPresenter presenter) {
        this.faqPresenter = presenter;
    }

    public void setPresenter(AncientOneInfoPresenter presenter) {
        this.ancientOneInfoPresenter = presenter;
    }

    private void initCategoryId() {
        if (Localization.getInstance().isRusLocale())
            categoryId = FAQ_CATEGORY_ID_RU;
        else
            categoryId = FAQ_CATEGORY_ID_EN;
    }

    public void getFaq() {
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
                        faqPresenter.setCurrentArticleList(response.getResponseData().getArticleData().getArticles());
                        Timber.d(String.valueOf(response.getResponseData().getArticleData().getArticles().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        faqPresenter.showErrorAlert(e);
                        faqPresenter.completeUpdateArticleList();
                        Timber.d("On Error");
                        Timber.d(e);
                    }

                    @Override
                    public void onComplete() {
                        faqPresenter.clickSearch(faqPresenter.getSearchText());
                        faqPresenter.completeUpdateArticleList();
                        faqPresenter.showSuccessAlert();
                        Timber.d("On Complete");
                    }
                });
    }

    public void getStories() {
        service.getArticles(OPTION, APP, RESOURCE, ANCIENT_ONE_STORY_CATEGORY_ID_RU, LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response response) {
                        ancientOneInfoPresenter.setStoryList(response.getResponseData().getArticleData().getArticles());
                        Timber.d(String.valueOf(response.getResponseData().getArticleData().getArticles().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ancientOneInfoPresenter.showErrorAlert(e);
                        ancientOneInfoPresenter.completeUpdateArticleList();
                        Timber.d("On Error");
                        Timber.d(e);
                    }

                    @Override
                    public void onComplete() {
                        //ancientOneInfoPresenter.clickSearch(ancientOneInfoPresenter.getSearchText());
//                        ancientOneInfoPresenter.completeUpdateArticleList();
//                        ancientOneInfoPresenter.showSuccessAlert();
                        getInfo();
                        Timber.d("On Complete");
                    }
                });
    }

    public void getInfo() {
        service.getArticles(OPTION, APP, RESOURCE, ANCIENT_ONE_INFO_CATEGORY_ID_RU, LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Response response) {
                        ancientOneInfoPresenter.setInfoList(response.getResponseData().getArticleData().getArticles());
                        Timber.d(String.valueOf(response.getResponseData().getArticleData().getArticles().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ancientOneInfoPresenter.showErrorAlert(e);
                        ancientOneInfoPresenter.completeUpdateArticleList();
                        Timber.d("On Error");
                        Timber.d(e);
                    }

                    @Override
                    public void onComplete() {
                        //faqPresenter.clickSearch(faqPresenter.getSearchText());
                        ancientOneInfoPresenter.completeUpdateArticleList();
                        ancientOneInfoPresenter.showSuccessAlert();
                        Timber.d("On Complete");
                    }
                });
    }
}