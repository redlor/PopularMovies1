package it.redlor.popularmovies1.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import dagger.android.AndroidInjection;
import it.redlor.popularmovies1.MoviesApp;

/**
 * Created by Hp on 24/02/2018.
 */

public class AppInjector {

    public AppInjector() {
    }

    public static void init(MoviesApp moviesApp) {

        DaggerAppComponent.builder().application(moviesApp)
                          .build().inject(moviesApp);

        moviesApp
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle bundle) {
                        AndroidInjection.inject(activity);
                     /*   if (activity instanceof HasSupportFragmentInjector)
                        {
                            AndroidInjection.inject(activity);
                        }
                        if (activity instanceof FragmentActivity)
                        {
                            ((FragmentActivity) activity).getSupportFragmentManager()
                                    .registerFragmentLifecycleCallbacks(
                                            new FragmentManager.FragmentLifecycleCallbacks()
                                            {
                                                @Override
                                                public void onFragmentCreated(FragmentManager fm, Fragment f,
                                                                              Bundle savedInstanceState)
                                                {
                                                    if (f instanceof Injectable)
                                                    {
                                                        AndroidSupportInjection.inject(f);
                                                    }
                                                }
                                            }, true);
                        }*/
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }

    /**
     * Marks an activity / fragment injectable.
     */

}
