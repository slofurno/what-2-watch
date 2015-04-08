package com.github.slofurno.what_2_watch.Events;

public class PatchUserActorsAsyncEvent {
    private int responseCode;
    private int result;

    public PatchUserActorsAsyncEvent(int actorId, int responseCode) {
        this.result = actorId;
        this.responseCode=responseCode;
    }

    public int getResult() {
        return result;
    }

    public int getResponseCode(){
        return responseCode;
    }
}
