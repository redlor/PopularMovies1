package it.redlor.popularmovies1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import it.redlor.popularmovies1.pojos.ResultMovie;
import it.redlor.popularmovies1.pojos.Root;
import it.redlor.popularmovies1.service.MovieApiClient;
import it.redlor.popularmovies1.service.PopularMoviesApiInterface;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = BuildConfig.API_KEY;

    @BindView(R.id.movies_rv)
    RecyclerView mRecyclerView;

    MovieRecyclerAdapter mAdapter;


    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        PopularMoviesApiInterface popularMoviesApiInterface = MovieApiClient.getClient().create(PopularMoviesApiInterface.class);

        // Calls the Interface for handling the custom request
mCompositeDisposable.add((Disposable) popularMoviesApiInterface.getRepository(API_KEY)
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
);

    }

    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
