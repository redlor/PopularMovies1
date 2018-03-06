package it.redlor.popularmovies1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import it.redlor.popularmovies1.R;
import it.redlor.popularmovies1.pojos.ResultMovie;

/**
 * Activity to show movie details.
 * It passes data to the Fragment
 */

public class DetailsActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String CLICKED_MOVIE = "clicked_movie";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        ResultMovie resultMovie = intent.getParcelableExtra(CLICKED_MOVIE);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CLICKED_MOVIE, resultMovie);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.details_container, detailsFragment)
                .commit();

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


}
