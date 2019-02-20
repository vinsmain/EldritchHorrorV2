package ru.mgusev.eldritchhorror.api;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.mgusev.eldritchhorror.api.model.category.Category;
import timber.log.Timber;


public class FaqAPIService {

    private Context context;
    private List<Category> allCurrencyList;

    public FaqAPIService(Context context) {
        this.context = context;
    }

    public void checkUpdate() {

        APIService service = FaqAPI.getClient(context).create(APIService.class);

        service.getCategory(9, "526b11779725fcceef927b69f2035ed7")
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
}
