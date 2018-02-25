package it.redlor.popularmovies1.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import it.redlor.popularmovies1.BuildConfig;
import it.redlor.popularmovies1.MovieRecyclerAdapter;
import it.redlor.popularmovies1.R;
import it.redlor.popularmovies1.pojos.ResultMovie;
import it.redlor.popularmovies1.viewmodel.MoviesListViewModel;
import it.redlor.popularmovies1.viewmodel.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = BuildConfig.API_KEY;

    @BindView(R.id.movies_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.no_internet_image)
    ImageView mNoInternetImageView;
    @BindView(R.id.no_internet_text)
    TextView mNoInternetTextView;

    MovieRecyclerAdapter mAdapter;

    MoviesListViewModel mViewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (internetAvailable()) {
            setOnlineUI();

        } else {
          setOfflineUI();
        }

        mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MoviesListViewModel.class);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.sort_movies,
                android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);

       mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));
        mAdapter = new MovieRecyclerAdapter(moviesList, this, new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ResultMovie result) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
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
        mSpinner.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoInternetImageView.setVisibility(View.GONE);
        mNoInternetTextView.setVisibility(View.GONE);
    }

    private void setOfflineUI() {
        mSpinner.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mNoInternetImageView.setVisibility(View.VISIBLE);
        mNoInternetTextView.setVisibility(View.VISIBLE);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mNoInternetImageView.getLayoutParams().height = 256;
            mNoInternetImageView.getLayoutParams().width = 256;
        }
    }
}
