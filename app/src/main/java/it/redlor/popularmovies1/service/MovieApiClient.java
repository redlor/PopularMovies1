package it.redlor.popularmovies1.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hp on 18/02/2018.
 */

public class MovieApiClient {

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static Retrofit mRetrofit =  null;


    public static Retrofit getClient() {
        if (mRetrofit == null) {
            // Initializing Http Interceptor to log the Url
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.build();

            // Build the Retrofit
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return mRetrofit;
    }


}
