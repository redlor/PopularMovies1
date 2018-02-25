package it.redlor.popularmovies1.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import it.redlor.popularmovies1.service.MoviesApiInterface;
import it.redlor.popularmovies1.service.TopRatedMoviesApiInterface;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hp on 23/02/2018.
 */

@Module
public class ApiClientModule {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";


   /* @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }*/

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
       // gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

 /*   @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        return client.build();
    }*/

    @Singleton
    @Provides
    Retrofit providesRetrofit(Gson gson) {

        // Initializing Http Interceptor to log the Url
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    @Singleton
    MoviesApiInterface providesMoviesApiInterface(Retrofit retrofit) {
        return retrofit.create(MoviesApiInterface.class);
    }

    @Provides
    @Singleton
    TopRatedMoviesApiInterface providesTopRatedMoviesApiInterface(Retrofit retrofit) {
        return retrofit.create(TopRatedMoviesApiInterface.class);
    }
}
