package com.github.slofurno.what_2_watch.Tasks;

import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.Events.PutUserMovieAsyncEvent;
import com.github.slofurno.what_2_watch.AppState.UserState;

import java.net.MalformedURLException;
import java.net.URL;

public class DeleteUserMovieAsync extends RestApiAsync {

    private UserState mUserState = UserState.getInstance();
    private int movieId;

    public DeleteUserMovieAsync(int movieId){
        this.movieId=movieId;
    }

    @Override protected URL getUrl() throws MalformedURLException {
        return new URL("http://gdf3.com:555/api/users/" + mUserState.mUserAccount.UserId + "/movies/" + movieId + "/delete");
    }

    @Override protected void onPostExecute(String result) {

        OttoBus.getInstance().post(new PutUserMovieAsyncEvent(null, mResponseCode));
    }
}
