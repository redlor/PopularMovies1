package it.redlor.popularmovies1.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import it.redlor.popularmovies1.viewmodel.MoviesListViewModel;
import it.redlor.popularmovies1.viewmodel.ViewModelFactory;
import it.redlor.popularmovies1.viewmodel.ViewModelKey;

/**
 * Created by Hp on 23/02/2018.
 */

@Module(includes = {ApiClientModule.class})
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesListViewModel.class)
    abstract ViewModel bindListVM(MoviesListViewModel moviesListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindVMFactory(ViewModelFactory viewModelFactory);
}
