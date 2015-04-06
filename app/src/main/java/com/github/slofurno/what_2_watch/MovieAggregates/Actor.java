package com.github.slofurno.what_2_watch.MovieAggregates;

import java.util.ArrayList;

/**
 * Created by slofurno on 4/4/2015.
 */
public class Actor
{
    public int ActorId;
    public String First;
    public String Last;
    public ArrayList<String> movies;

    public Actor(){

    }

    public Actor(int id, String first, String last){
        ActorId=id;
        First=first;
        Last=last;
    }

    public String toString(){
        return First + "ZZ " + Last;
    }
}
