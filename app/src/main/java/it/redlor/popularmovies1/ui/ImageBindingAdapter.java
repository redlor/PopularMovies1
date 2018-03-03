package it.redlor.popularmovies1.ui;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Hp on 03/03/2018.
 */

public class ImageBindingAdapter {

    private static final String BASE_THUMBNAIL_URL = "http://image.tmdb.org/t/p/";
    private static final String THUMBNAIL_SIZE = "w185";

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url, String posterPath) {
        url = BASE_THUMBNAIL_URL + THUMBNAIL_SIZE + posterPath;
        if (!url.equals("")) {
            Log.d("Image ", url);
            Picasso.with(imageView.getContext()).load(url).into(imageView);
        }
    }
}
