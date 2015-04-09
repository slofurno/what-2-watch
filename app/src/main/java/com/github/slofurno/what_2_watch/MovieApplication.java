package com.github.slofurno.what_2_watch;

import android.app.Application;

import com.github.slofurno.what_2_watch.Activities.LoginActivity;
import com.github.slofurno.what_2_watch.Activities.MainActivity;
import com.github.slofurno.what_2_watch.AppState.AccountManager;
import com.github.slofurno.what_2_watch.AppState.ActorManager;
import com.github.slofurno.what_2_watch.AppState.MovieManager;
import com.github.slofurno.what_2_watch.Tabs.ActorSearchTab;
import com.github.slofurno.what_2_watch.Tabs.RecommendationsTab;
import com.github.slofurno.what_2_watch.Tabs.WatchedMoviesTab;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slofurno on 4/8/2015.
 */

public class MovieApplication extends Application {

    @Singleton
    @Component(modules = MovieApplicationModule.class)
    public interface ApplicationComponent {
        void inject(MovieApplication application);
        void inject(BaseActivity baseActivity);
        void inject(LoginActivity loginActivity);
        void inject(MainActivity mainActivity);
        void inject(BaseFragment baseFragment);
        void inject(WatchedMoviesTab watchedMoviesTab);
        void inject(RecommendationsTab recommendationsTab);
        void inject(ActorSearchTab actorSearchTab);

    }

    private ApplicationComponent component;

    @Inject
    MovieManager locationManager;

    @Inject
    ActorManager actorManager;

    @Inject
    AccountManager accountManager;

    @Override public void onCreate() {
        super.onCreate();

        component = DaggerMovieApplication_ApplicationComponent.builder()
                .movieApplicationModule(new MovieApplicationModule(this))
                .build();
/*
        applicationComponent = DaggerApplicationComponent.builder()
                .movieApplicationModule(new MovieApplicationModule(this))
                .build();
*/
        component().inject(this);
    }

    public ApplicationComponent component() {
        return component;
    }
}
