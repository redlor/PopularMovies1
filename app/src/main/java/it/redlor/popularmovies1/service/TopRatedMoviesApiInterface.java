package it.redlor.popularmovies1.service;

import io.reactivex.Observable;
import it.redlor.popularmovies1.pojos.Root;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hp on 18/02/2018.
 */

public interface TopRatedMoviesApiInterface {

    // Customize the Url request
    @GET("top_rated")
    Observable<Root> getRepository(@Query("api_key") String apyKey);
}
