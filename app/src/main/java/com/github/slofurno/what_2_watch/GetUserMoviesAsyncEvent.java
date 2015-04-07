package com.github.slofurno.what_2_watch;

import com.github.slofurno.what_2_watch.MovieAggregates.Movie;

import java.util.List;

/**
 * Created by slofurno on 4/7/2015.
 */
public class GetUserMoviesAsyncEvent {

    private List<Movie> result;
    private int responseCode;

    public GetUserMoviesAsyncEvent(List<Movie> movies, int mResponseCode) {

        this.result=movies;
        this.responseCode=mResponseCode;
    }

    public List<Movie> getResult() {
        return result;
    }

    public int getResponseCode(){
        return responseCode;
    }
}
