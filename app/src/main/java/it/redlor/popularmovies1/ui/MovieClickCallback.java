package it.redlor.popularmovies1.ui;

import it.redlor.popularmovies1.pojos.ResultMovie;

/**
 * Created by Hp on 27/02/2018.
 */

public interface MovieClickCallback extends MovieRecyclerAdapter.ViewHolderListener {
    void onClick(ResultMovie resultMovie);
}
