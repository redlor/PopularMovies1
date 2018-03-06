package it.redlor.popularmovies1.ui;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.redlor.popularmovies1.BR;
import it.redlor.popularmovies1.databinding.ListItemBinding;
import it.redlor.popularmovies1.pojos.ResultMovie;
import it.redlor.popularmovies1.service.AnimationUtil;
import it.redlor.popularmovies1.viewmodel.MoviesListViewModel;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private static final String BASE_THUMBNAIL_URL = "http://image.tmdb.org/t/p/";
    private static final String THUMBNAIL_SIZE = "w185/";

    private int previousPosition = 0;

    private MoviesListViewModel mMoviesListViewModel;
    private List<ResultMovie> mResultMovieList;
    private MovieClickCallback mMovieClickCallback;


    public MovieRecyclerAdapter(List<ResultMovie> list, MoviesListViewModel moviesListViewModel, MovieClickCallback movieClickCallback) {
        mResultMovieList = new ArrayList<>();
        this.mResultMovieList = list;
        this.mMoviesListViewModel = moviesListViewModel;
        this.mMovieClickCallback = movieClickCallback;
    }

    public void setData(List<ResultMovie> moviesList) {
        this.mResultMovieList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return MovieViewHolder.create(layoutInflater, parent, mMovieClickCallback);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        // Set the scrolling animation on the RecyclerView
        if (position > previousPosition) {
            AnimationUtil.animate(holder, true); // We are scrolling DOWN
        } else {
            AnimationUtil.animate(holder, false); // We are scrolling UP
        }
        previousPosition = position;

        holder.bind(mResultMovieList.get(position), mMovieClickCallback);
    }

    @Override
    public int getItemCount() {
        return mResultMovieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding mViewDataBinding;

        public MovieViewHolder(final ListItemBinding listItemBinding, final MovieClickCallback callback) {
            super(listItemBinding.getRoot());
            this.mViewDataBinding = listItemBinding;
            listItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(listItemBinding.getViewModel());
                }
            });

        }

        public static MovieViewHolder create(LayoutInflater inflater, ViewGroup parent, MovieClickCallback callback) {
            ListItemBinding itemMovieListBinding = ListItemBinding.inflate(inflater, parent, false);
            return new MovieViewHolder(itemMovieListBinding, callback);
        }

        // Bind the onItemClickListener to the RecyclerView
        public void bind(Object viewModel, MovieClickCallback callback) {
            mViewDataBinding.setVariable(BR.viewModel, viewModel);
            mViewDataBinding.setVariable(BR.callback, callback);
            mViewDataBinding.executePendingBindings();

        }
    }

}
