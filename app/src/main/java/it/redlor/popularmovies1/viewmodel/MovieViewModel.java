package it.redlor.popularmovies1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import it.redlor.popularmovies1.pojos.ResultMovie;

/**
 * Created by Hp on 25/02/2018.
 */

public class MovieViewModel extends ViewModel {

    private Application mApplication;
    MutableLiveData<ResultMovie> mResultMovie;

    @Inject
    public MovieViewModel(Application application) {
        this.mApplication = application;
        this.mResultMovie = new MutableLiveData<>();
    }

    public Application getApplication() {
        return mApplication;
    }

    public void setApplication(Application mApplication) {
        this.mApplication = mApplication;
    }

    public ResultMovie getResultMovie() {
        return mResultMovie.getValue();
    }

    public void setResultMovie(ResultMovie resultMovie) {
        this.mResultMovie.setValue(resultMovie);
    }


}
