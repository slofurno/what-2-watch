package com.github.slofurno.what_2_watch.Events;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;

import java.util.List;

public class GetActorsAsyncEvent {
    private int responseCode;
    private List<Actor> result;

    public GetActorsAsyncEvent(List<Actor> result, int responseCode) {
        this.result = result;
        this.responseCode=responseCode;
    }

    public List<Actor> getResult() {
        return result;
    }

    public int getResponseCode(){
        return responseCode;
    }
}


