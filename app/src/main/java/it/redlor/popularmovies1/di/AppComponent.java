package it.redlor.popularmovies1.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import it.redlor.popularmovies1.MoviesApp;

/**
 * Created by Hp on 24/02/2018.
 */

@Singleton
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        BuildersModule.class, FragmentBuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MoviesApp moviesApp);

        AppComponent build();
    }

    void inject(MoviesApp moviesApp);
}
