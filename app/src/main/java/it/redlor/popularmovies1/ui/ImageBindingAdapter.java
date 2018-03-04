package it.redlor.popularmovies1.ui;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import it.redlor.popularmovies1.R;

/**
 * Created by Hp on 03/03/2018.
 */

public class ImageBindingAdapter {

    private static final String BASE_THUMBNAIL_URL = "http://image.tmdb.org/t/p/";
    private static final String THUMBNAIL_SIZE = "w342";

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {

//            Log.d("Image ", imageUrl);
            Uri uri = Uri.parse(BASE_THUMBNAIL_URL).buildUpon()
                    .appendPath(THUMBNAIL_SIZE)
                    .appendEncodedPath(imageUrl)
                    .build();
            Picasso.with(imageView.getContext())
                    .load(uri.toString())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
    }
}
