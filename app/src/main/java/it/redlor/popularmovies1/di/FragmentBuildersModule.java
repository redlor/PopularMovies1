package it.redlor.popularmovies1.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import it.redlor.popularmovies1.ui.DetailsFragment;

/**
 * Created by Hp on 03/03/2018.
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract DetailsFragment bindFragmentDetails();
}
