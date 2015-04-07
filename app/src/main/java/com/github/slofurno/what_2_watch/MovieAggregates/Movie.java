package com.github.slofurno.what_2_watch.MovieAggregates;

/**
 * Created by slofurno on 4/4/2015.
 */
public class Movie {

    public int MovieId;
    public String title;
    public String year;
    public float rating;
    public int votes;

    public Movie(){

    }

    public Movie(String title, float rating ){
        this.title=title;
        this.rating=rating;
    }

    public String toString(){

        return title + "  " +  rating;
    }

}
