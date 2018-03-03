package it.redlor.popularmovies1.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import it.redlor.popularmovies1.R;
import it.redlor.popularmovies1.databinding.FragmentDetailsBinding;
import it.redlor.popularmovies1.di.Injectable;
import it.redlor.popularmovies1.viewmodel.MovieViewModel;
import it.redlor.popularmovies1.viewmodel.ViewModelFactory;


/**
 * Created by Hp on 25/02/2018.
 */

public class DetailsFragment extends Fragment implements Injectable {

    FragmentDetailsBinding fragmentDetailsBinding;

    @Inject
    ViewModelFactory viewModelFactory;

    MovieViewModel movieViewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

movieViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);

        return fragmentDetailsBinding.getRoot();
    }
}
