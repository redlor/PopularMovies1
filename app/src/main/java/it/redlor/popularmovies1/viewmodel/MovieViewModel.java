package it.redlor.popularmovies1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import it.redlor.popularmovies1.pojos.ResultMovie;

/**
 * ViewModel for the Details Screen
 */

public class MovieViewModel extends ViewModel {

    MutableLiveData<ResultMovie> resultMovie;
    private Application application;

    @Inject
    public MovieViewModel(Application application) {
        this.application = application;
        this.resultMovie = new MutableLiveData<>();
    }

    public ResultMovie getResultMovie() {
        return resultMovie.getValue();
    }

    public void setResultMovie(ResultMovie resultMovie) {
        this.resultMovie.setValue(resultMovie);
    }


}
