package com.github.slofurno.what_2_watch;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;

import java.util.List;

public class GetUserActorsAsyncEvent {
    private int responseCode;
    private List<Actor> result;

    public GetUserActorsAsyncEvent(List<Actor> result, int responseCode) {
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

