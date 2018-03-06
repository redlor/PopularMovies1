package it.redlor.popularmovies1.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import it.redlor.popularmovies1.BR;
import it.redlor.popularmovies1.R;
import it.redlor.popularmovies1.databinding.FragmentDetailsBinding;
import it.redlor.popularmovies1.di.Injectable;
import it.redlor.popularmovies1.pojos.ResultMovie;
import it.redlor.popularmovies1.viewmodel.MovieViewModel;
import it.redlor.popularmovies1.viewmodel.ViewModelFactory;


/**
 * Fragment with Movies's details
 */

public class DetailsFragment extends Fragment implements Injectable {

    private static final String CLICKED_MOVIE = "clicked_movie";

    FragmentDetailsBinding fragmentDetailsBinding;

    @Inject
    ViewModelFactory viewModelFactory;

    MovieViewModel movieViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        return fragmentDetailsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = getArguments();
        ResultMovie resultMovie = bundle.getParcelable(CLICKED_MOVIE);

        movieViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);
        movieViewModel.setResultMovie(resultMovie);
        fragmentDetailsBinding.setVariable(BR.movieViewModel, movieViewModel);
        getActivity().setTitle(resultMovie.getTitle());


    }
}
