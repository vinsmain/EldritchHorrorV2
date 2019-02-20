package ru.mgusev.eldritchhorror.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mgusev.eldritchhorror.api.model.category.Category;

public interface APIService {

    @GET("index.php?option=com_api&app=categories&resource=category&format=raw/")
    Observable<Category> getCategory(@Query("id") long id, @Query("key") String key);
}
