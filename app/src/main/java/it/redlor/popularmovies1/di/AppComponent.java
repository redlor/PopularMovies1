package it.redlor.popularmovies1.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import it.redlor.popularmovies1.MoviesApp;

/**
 * Dagger Component for the application
 */

@Singleton
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        BuildersModule.class, FragmentBuildersModule.class})
public interface AppComponent {
    void inject(MoviesApp moviesApp);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MoviesApp moviesApp);

        AppComponent build();
    }
}
