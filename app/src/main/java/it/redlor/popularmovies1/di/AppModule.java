package it.redlor.popularmovies1.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import it.redlor.popularmovies1.MoviesApp;

/**
 * Created by Hp on 23/02/2018.
 */

@Module(includes = {AndroidInjectionModule.class, ViewModelModule.class})
public class AppModule {


    @Provides
    Application provideContext(MoviesApp application) {
        return application;
    }


}
