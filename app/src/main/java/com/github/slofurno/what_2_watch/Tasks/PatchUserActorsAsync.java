package com.github.slofurno.what_2_watch.Tasks;

import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.Events.PatchUserActorsAsyncEvent;
import com.github.slofurno.what_2_watch.AppState.UserState;

import java.net.MalformedURLException;
import java.net.URL;

public class PatchUserActorsAsync extends RestApiAsync {

    private UserState mUserState = UserState.getInstance();
    private int mActorId;

    public PatchUserActorsAsync(int actorId){
        mActorId=actorId;
    }

    @Override protected URL getUrl() throws MalformedURLException {
        UserState userState = UserState.getInstance();
        UserAccount ua = userState.mUserAccount;

        return new URL("http://gdf3.com:555/api/users/" + ua.UserId + "/actors/"+ mActorId);
    }
//TODO:instead of using actorid, use Actor instance, and return new state (selected or not)
    @Override protected void onPostExecute(String result) {

        OttoBus.getInstance().post(new PatchUserActorsAsyncEvent(mActorId, mResponseCode));
    }
}
