package com.github.slofurno.what_2_watch.Events;

import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;

public class PutUserAccountAsyncEvent {
    private int responseCode;
    private UserAccount result;

    public PutUserAccountAsyncEvent(UserAccount result, int responseCode) {
        this.result = result;
        this.responseCode=responseCode;
    }

    public UserAccount getResult() {
        return result;
    }

    public int getResponseCode(){
        return responseCode;
    }
}

