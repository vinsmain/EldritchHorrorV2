package ru.mgusev.eldritchhorror.api;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.mgusev.eldritchhorror.api.json_model.Response;
import ru.mgusev.eldritchhorror.presentation.presenter.faq.FaqPresenter;
import timber.log.Timber;


public class FaqAPIService {

    private Context context;
    private FaqPresenter presenter;

    private long CATEGORY_ID = 9;
    private String API_KEY = "526b11779725fcceef927b69f2035ed7";

    public FaqAPIService(Context context) {
        this.context = context;
    }

    public void setPresenter(FaqPresenter presenter) {
        this.presenter = presenter;
    }

    public void getArticles() {

        APIService service = FaqAPI.getClient(context).create(APIService.class);

        service.getArticles(CATEGORY_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response response) {
                        //allCurrencyList = new ArrayList<>(coinList.getData().values());
                        presenter.setArticleList(response.getData().getData().getArticles());
                        Timber.d(String.valueOf(response.getData().getData().getArticles().size()));
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
