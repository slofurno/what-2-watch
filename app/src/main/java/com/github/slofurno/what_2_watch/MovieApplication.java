package com.github.slofurno.what_2_watch;

import android.app.Application;

import com.github.slofurno.what_2_watch.Activities.LoginActivity;
import com.github.slofurno.what_2_watch.AppState.AccountManager;
import com.github.slofurno.what_2_watch.AppState.ActorManager;
import com.github.slofurno.what_2_watch.AppState.MovieManager;

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
