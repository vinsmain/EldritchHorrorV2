package ru.mgusev.eldritchhorror.api;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.mgusev.eldritchhorror.api.model.article.Article;
import ru.mgusev.eldritchhorror.api.model.category.Category;
import ru.mgusev.eldritchhorror.presentation.presenter.faq.FaqPresenter;
import timber.log.Timber;


public class FaqAPIService {

    private Context context;
    private List<Category> allCurrencyList;
    private FaqPresenter presenter;

    private long CATEGORY_ID = 9;
    private String API_KEY = "526b11779725fcceef927b69f2035ed7";

    public FaqAPIService(Context context) {
        this.context = context;
    }

    public void setPresenter(FaqPresenter presenter) {
        this.presenter = presenter;
    }

    public void checkUpdate() {

        APIService service = FaqAPI.getClient(context).create(APIService.class);

        service.getCategory(CATEGORY_ID, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Category>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Category category) {
                        //allCurrencyList = new ArrayList<>(coinList.getData().values());
                        Timber.d(String.valueOf(category.getData().getModifiedTime()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("On Error");
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        //cPresenter.updateCoinList(allCurrencyList);
                        Timber.d("On Complete");
                    }
                });
    }

    public void getArticles() {

        APIService service = FaqAPI.getClient(context).create(APIService.class);

        service.getArticles(CATEGORY_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Article article) {
                        //allCurrencyList = new ArrayList<>(coinList.getData().values());
                        presenter.updateArticleList(article);
                        Timber.d(String.valueOf(article.getData().getData().getResults().size()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("On Error");
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        //cPresenter.updateCoinList(allCurrencyList);
                        Timber.d("On Complete");
                    }
                });
    }
}
