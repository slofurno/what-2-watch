package com.github.slofurno.what_2_watch.AppState;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by slofurno on 4/8/2015.
 */
@Singleton
public class MovieManager {
    private HashMap<Integer,Movie> watchedMovies = new HashMap<>();

    public boolean hasWatched(Movie movie) {
        return watchedMovies.containsKey(movie.MovieId);
    }

    public void watch(Movie movie) {
        watchedMovies.put(movie.MovieId, movie);
    }

    public void unwatch(Movie movie) {
        watchedMovies.remove(movie.MovieId);
    }

    public List<Movie> getWatched(){
        Collection<Movie> movies = watchedMovies.values();
        return new ArrayList<Movie>(movies);
    }

}


