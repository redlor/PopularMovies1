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

    private Application application;
    MutableLiveData<ResultMovie> resultMovie;

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
