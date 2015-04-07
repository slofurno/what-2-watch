package com.github.slofurno.what_2_watch;

import com.github.slofurno.what_2_watch.MovieAggregates.Movie;

public class PutUserMovieAsyncEvent {
    private int responseCode;
    private Movie result;

    public PutUserMovieAsyncEvent(Movie result, int responseCode) {
        this.result = result;
        this.responseCode=responseCode;
    }

    public Movie getResult() {
        return result;
    }

    public int getResponseCode(){
        return responseCode;
    }
}
