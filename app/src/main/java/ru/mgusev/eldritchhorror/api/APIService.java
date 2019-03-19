package ru.mgusev.eldritchhorror.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mgusev.eldritchhorror.api.json_model.Response;

public interface APIService {

    @GET("index.php")
    Observable<Response> getArticles(@Query("option") String option, @Query("app") String app, @Query("resource") String resource, @Query("category_id") long id, @Query("limit") long limit);
}
