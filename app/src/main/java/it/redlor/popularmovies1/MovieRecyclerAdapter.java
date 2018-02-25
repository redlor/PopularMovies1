package it.redlor.popularmovies1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.redlor.popularmovies1.pojos.ResultMovie;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private static final String BASE_THUMBNAIL_URL = "http://image.tmdb.org/t/p/";
    private static final String THUMBNAIL_SIZE = "w185";

    private Context mContext;
    private List<ResultMovie> mResultMovieList;
    private OnItemClickListener mListener;

    public MovieRecyclerAdapter(List<ResultMovie> resultMovieList, Context context, OnItemClickListener listener) {
        this.mResultMovieList = resultMovieList;
        this.mContext =  context;
        this.mListener = listener;
    }


    public void setData(List<ResultMovie> moviesList) {
        this.mResultMovieList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final ResultMovie currentMovie = mResultMovieList.get(position);

        // Populate the ViewHolder
        String imageUrl = BASE_THUMBNAIL_URL + THUMBNAIL_SIZE + currentMovie.getPosterPath();
        Log.d(MovieRecyclerAdapter.class.getSimpleName(), imageUrl);
        Picasso.with(mContext).load(imageUrl).into(holder.movieThumbnailIv);

        holder.bind(mResultMovieList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mResultMovieList.size();
    }

    // Create an onItemClickListener for the RecyclerView
    public interface OnItemClickListener {
        void onItemClick(ResultMovie result);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_thumbnail)
        ImageView movieThumbnailIv;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        // Bind the onItemClickListener to the RecyclerView
        void bind(final ResultMovie resultMovie, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(resultMovie);
                }
            });
        }
    }

}
