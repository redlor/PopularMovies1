package it.redlor.popularmovies1.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import it.redlor.popularmovies1.ui.DetailsActivity;
import it.redlor.popularmovies1.ui.MainActivity;
import it.redlor.popularmovies1.ui.SplashScreenActivity;

/**
 * Module for the activities
 */

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract DetailsActivity bindDetailsActivity();

    @ContributesAndroidInjector
    abstract SplashScreenActivity bindSplashActivity();
}
