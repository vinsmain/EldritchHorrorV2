package ru.mgusev.eldritchhorror.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mgusev.eldritchhorror.api.json_model.Response;

public interface APIService {

    @GET("index.php?option=com_api&app=articles&resource=article&category_id=9")
    Observable<Response> getArticles(@Query("category_id") long id);
}
