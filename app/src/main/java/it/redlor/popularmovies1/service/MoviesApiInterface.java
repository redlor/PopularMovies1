package it.redlor.popularmovies1.service;

import io.reactivex.Observable;
import it.redlor.popularmovies1.pojos.Root;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface to define endpoints
 */

public interface MoviesApiInterface {

    // Customize the Url request
    @GET("popular")
    Observable<Root> getRepository(@Query("api_key") String apiKey);

    @GET("top_rated")
    Observable<Root> getTopRatedRepo(@Query("api_key") String apiKey);
}
