package com.github.slofurno.what_2_watch;

import android.app.Application;

import com.github.slofurno.what_2_watch.AppState.AccountManager;
import com.github.slofurno.what_2_watch.AppState.ActorManager;
import com.github.slofurno.what_2_watch.AppState.MovieManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slofurno on 4/8/2015.
 */
@Module
public class MovieApplicationModule {
    private final Application application;

    public MovieApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Expose the application to the graph.
     */
    @Provides @Singleton @ForApplication
    Application application() {
        return application;
    }

    @Provides @Singleton
    MovieManager provideMovieManager() {
        return new MovieManager();
    }

    @Provides @Singleton
     ActorManager provideActorManager() {
        return new ActorManager();
    }

    @Provides @Singleton
    AccountManager provideAccountManager() {
        return AccountManager.getInstance();
    }

}