package it.redlor.popularmovies1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.redlor.popularmovies1.BuildConfig;
import it.redlor.popularmovies1.pojos.ResultMovie;
import it.redlor.popularmovies1.pojos.Root;
import it.redlor.popularmovies1.service.MoviesApiInterface;
import it.redlor.popularmovies1.ui.DetailsActivity;
import it.redlor.popularmovies1.ui.MovieClickCallback;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Hp on 20/02/2018.
 */

public class MoviesListViewModel extends ViewModel implements MovieClickCallback {

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String CLICKED_MOVIE = "clicked_movie";


    private Application mApplication;
    private MoviesApiInterface mMoviesApiInterface;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<ResultMovie>> mMoviesList;



    @Inject
    public MoviesListViewModel(MoviesApiInterface popularMoviesApiInterface, Application application) {
        this.mMoviesApiInterface = popularMoviesApiInterface;
        this.mApplication = application;
        mMoviesList = new MutableLiveData<>();
    }



    public LiveData<List<ResultMovie>> getMostPopularMoviesList() {
            loadMovies();
        return mMoviesList;
    }

    public LiveData<List<ResultMovie>> getTopRatedMoviesList() {
        loadTopRatedMovies();
        return mMoviesList;
    }

    public void setMoviesList(MutableLiveData<List<ResultMovie>> mMoviesList) {
        this.mMoviesList = mMoviesList;
    }

    private void loadTopRatedMovies() {

        mCompositeDisposable.add((Disposable) mMoviesApiInterface.getTopRatedRepo(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Root>() {
                    @Override
                    public void accept(Root root) throws Exception {
                        mMoviesList.setValue(new ArrayList<>());
                        for (ResultMovie resultMovie : root.getResults()) {
                            if (root.getResults() != null) mMoviesList.getValue().add(resultMovie);
                        }

                    }
                }));
    }

    private void loadMovies() {

        mCompositeDisposable.add((Disposable) mMoviesApiInterface.getRepository(API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Root>() {
                    @Override
                    public void accept(Root root) throws Exception {
                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                        httpClient.addInterceptor(logging);
                        httpClient.build();
                        mMoviesList.setValue(new ArrayList<>());
                        for (ResultMovie resultMovie : root.getResults()) {
                            if (root.getResults() != null) mMoviesList.getValue().add(resultMovie);
                        }

                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

    @Override
    public void onClick(ResultMovie resultMovie) {
        Intent intent = new Intent(mApplication.getApplicationContext(), DetailsActivity.class);
        intent.putExtra(CLICKED_MOVIE, resultMovie);
        mApplication.startActivity(intent);
    }
}
