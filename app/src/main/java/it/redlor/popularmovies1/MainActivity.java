package it.redlor.popularmovies1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import it.redlor.popularmovies1.pojos.ResultMovie;
import it.redlor.popularmovies1.viewmodel.MoviesListViewModel;
import it.redlor.popularmovies1.viewmodel.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = BuildConfig.API_KEY;

    @BindView(R.id.movies_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.spinner)
    Spinner mSpinner;

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
                       mViewModel.getMoviesList().observe(MainActivity.this, mMoviesList -> processResponse(mMoviesList));
                       break;
                   case 1:
                       mViewModel.getTopRatedMoviesList().observe(MainActivity.this, mMoviesList -> processResponse(mMoviesList));
               }


           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


  //   mViewModel.getMoviesList().observe(this, mMoviesList -> processResponse(mMoviesList));



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
}
