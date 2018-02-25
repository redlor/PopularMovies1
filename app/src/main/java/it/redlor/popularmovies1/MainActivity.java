package it.redlor.popularmovies1;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

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


       // AppComponent appComponent = ((MoviesApp) getApplication()).getAppComponent();
//        AndroidInjection.inject(this);
     //   androidInjector.inject(this);

        mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MoviesListViewModel.class);
    System.out.println("viewmodel: " + mViewModel.toString());

     mViewModel.getMoviesList().observe(this, mMoviesList -> {
         if (mMoviesList == null) {
             System.out.println(" null");
         }
         int numberOfColumns = calculateNoOfColumns(getApplicationContext());
         mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));
         mAdapter = new MovieRecyclerAdapter(mMoviesList, this, new MovieRecyclerAdapter.OnItemClickListener() {
             @Override
             public void onItemClick(ResultMovie result) {

             }
         });
         mRecyclerView.setAdapter(mAdapter);


     });

                // mViewModel.getMoviesList().observe(this, mMoviesList -> processResponse(mMoviesList));

                //    mViewModel.getMovies().observe(this, mMoviesList -> mAdapter.setData(mMoviesList));

                //    mViewModel.getMoviesList().observe(this, mMoviesList -> mAdapter.setData(mMoviesList));


/*mCompositeDisposable.add((Disposable) moviesApiInterface.getRepository(API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<Root, List<ResultMovie>>() {
                        @Override
                        public List<ResultMovie> apply(Root root) throws Exception {
                            return root.getResults();
                        }
                    })
                    .subscribe(new Consumer<List<ResultMovie>>() {
                        @Override
                        public void accept(List<ResultMovie> resultMovieList) throws Exception {
                            int numberOfColumns = calculateNoOfColumns(getApplicationContext());
                            mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));
                            mAdapter = new MovieRecyclerAdapter(resultMovieList, MainActivity.this, new MovieRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(ResultMovie result) {

                                }
                            });
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    })
);*/

    }

    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

  /*  private void processResponse(List<ResultMovie> moviesList) {
        mViewModel.getPopularMovies();
        int numberOfColumns = calculateNoOfColumns(getApplicationContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns));
        mAdapter = new MovieRecyclerAdapter(moviesList, mViewModel, new MovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ResultMovie result) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        System.out.println(moviesList.toString());
    }*/
}
