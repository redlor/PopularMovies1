package it.redlor.popularmovies1.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import it.redlor.popularmovies1.ui.DetailsFragment;

/**
 * Module for the Fragment
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract DetailsFragment bindFragmentDetails();
}
