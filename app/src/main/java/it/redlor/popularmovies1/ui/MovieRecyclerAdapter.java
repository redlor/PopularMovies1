package it.redlor.popularmovies1.ui;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
  //  private OnItemClickListener mListener;


    public MovieRecyclerAdapter(List<ResultMovie> resultMovieList, MoviesListViewModel moviesListViewModel) {
        this.mResultMovieList = resultMovieList;
        this.mMoviesListViewModel = moviesListViewModel;
    }

    public void setData(List<ResultMovie> moviesList) {
        this.mResultMovieList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(listItemView);*/
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemBinding listItemBinding = ListItemBinding.inflate(layoutInflater,
                parent, false);
        return new MovieViewHolder(listItemBinding);

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (position > previousPosition) {
            AnimationUtil.animate(holder, true); // We are scrolling DOWN
        } else {
            AnimationUtil.animate(holder, false); // We are scrolling UP
        }
        previousPosition = position;


        holder.bind(mResultMovieList.get(position), mMoviesListViewModel);
    }

    @Override
    public int getItemCount() {
        return mResultMovieList.size();
    }

    // Create an onItemClickListener for the RecyclerView
   /* public interface OnItemClickListener {
        void onItemClick(ResultMovie result);
    }*/

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding mViewDataBinding;

        public MovieViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.mViewDataBinding =  viewDataBinding;
        }

        // Bind the onItemClickListener to the RecyclerView
        public void bind(Object viewModel, MovieClickCallback callback) {
            mViewDataBinding.setVariable(BR.viewModel, viewModel);
            mViewDataBinding.setVariable(BR.callback, callback);
            mViewDataBinding.executePendingBindings();

        }


    }
    public interface ViewHolderListener {

    }
}
