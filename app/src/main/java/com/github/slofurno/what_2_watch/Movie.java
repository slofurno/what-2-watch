package com.github.slofurno.what_2_watch;

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

    public String toString(){

        return title + " " + year + "\r" + rating;

    }

}
