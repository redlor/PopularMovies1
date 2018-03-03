package it.redlor.popularmovies1.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import javax.inject.Inject;

import it.redlor.popularmovies1.R;
import it.redlor.popularmovies1.databinding.ActivityMainBinding;
import it.redlor.popularmovies1.pojos.ResultMovie;
import it.redlor.popularmovies1.viewmodel.MoviesListViewModel;
import it.redlor.popularmovies1.viewmodel.ViewModelFactory;

public class MainActivity extends AppCompatActivity {


    MovieRecyclerAdapter mAdapter;
    MoviesListViewModel mViewModel;
    ActivityMainBinding mActivityMainBinding;

    @Inject
    ViewModelFactory mViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (internetAvailable()) {
            setOnlineUI();

        } else {
          setOfflineUI();
        }

        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(MoviesListViewModel.class);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.sort_movies,
                android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       mActivityMainBinding.spinner.setAdapter(arrayAdapter);

        mActivityMainBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               switch (i) {
                   case 0:
                       if (internetAvailable()) {
                           setOnlineUI();
                           mViewModel.getMostPopularMoviesList().observe(MainActivity.this, mMoviesList -> processResponse(mMoviesList));
                       } else {
                           setOfflineUI();
                       }
                       break;
                   case 1:
                       if (internetAvailable()) {
                           setOnlineUI();
                           mViewModel.getTopRatedMoviesList().observe(MainActivity.this, mMoviesList -> processResponse(mMoviesList));
                       } else {
                           setOfflineUI();
                       }
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
           }
       });


    }

    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    private void processResponse(List<ResultMovie> moviesList) {
        int numberOfColumns = calculateNoOfColumns(getApplicationContext());
        mActivityMainBinding.moviesRv.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));
        mAdapter = new MovieRecyclerAdapter(moviesList, mViewModel);
        mActivityMainBinding.moviesRv.setAdapter(mAdapter);
    }

    public boolean internetAvailable() {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void setOnlineUI() {
        mActivityMainBinding.spinner.setVisibility(View.VISIBLE);
        mActivityMainBinding.moviesRv.setVisibility(View.VISIBLE);
        mActivityMainBinding.noInternetImage.setVisibility(View.GONE);
        mActivityMainBinding.noInternetText.setVisibility(View.GONE);
    }

    private void setOfflineUI() {
        mActivityMainBinding.spinner.setVisibility(View.GONE);
        mActivityMainBinding.moviesRv.setVisibility(View.GONE);
        mActivityMainBinding.noInternetImage.setVisibility(View.VISIBLE);
        mActivityMainBinding.noInternetText.setVisibility(View.VISIBLE);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mActivityMainBinding.noInternetImage.getLayoutParams().height = 256;
            mActivityMainBinding.noInternetImage.getLayoutParams().width = 256;
        }
    }

}
