package it.redlor.popularmovies1.service;

import it.redlor.popularmovies1.pojos.Root;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hp on 18/02/2018.
 */

public interface TopRatedMoviesApiInterface {

    // Customize the Url request
    @GET("top_rated")
    Call<Root> getRepository(@Query("page") String page,
                             @Query("language") String language,
                             @Query("api_key") String apyKey);
}
