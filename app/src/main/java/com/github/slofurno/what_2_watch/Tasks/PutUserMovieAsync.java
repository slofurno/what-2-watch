package com.github.slofurno.what_2_watch.Tasks;

import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.Events.PutUserMovieAsyncEvent;

import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;

import java.net.MalformedURLException;
import java.net.URL;

public class PutUserMovieAsync extends RestApiAsync {

    private int movieId;

    public PutUserMovieAsync(int movieId){
        this.movieId=movieId;
    }

    @Override protected URL getUrl() throws MalformedURLException {
        UserAccount ua = accountManager.getUserAccount();
        return new URL("http://gdf3.com:555/api/users/" + ua.UserId + "/movies/" + movieId);
    }

    @Override protected void onPostExecute(String result) {

        OttoBus.getInstance().post(new PutUserMovieAsyncEvent(null, mResponseCode));
    }
}


