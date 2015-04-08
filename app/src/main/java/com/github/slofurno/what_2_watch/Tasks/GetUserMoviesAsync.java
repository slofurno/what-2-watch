package com.github.slofurno.what_2_watch.Tasks;

import com.github.slofurno.what_2_watch.Events.GetUserMoviesAsyncEvent;
import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetUserMoviesAsync extends RestApiAsync {

    @Override protected URL getUrl() throws MalformedURLException {
        UserAccount ua = accountManager.getUserAccount();
        return new URL("http://gdf3.com:555/api/users/" + ua.UserId + "/movies");
    }

    @Override protected void onPostExecute(String result) {

        JsonReader reader = new JsonReader(new StringReader(result));
        reader.setLenient(true);
        Gson gson = new Gson();
        List<Movie> movies = gson.fromJson(reader, new TypeToken<ArrayList<Movie>>(){}.getType());

        OttoBus.getInstance().post(new GetUserMoviesAsyncEvent(movies, mResponseCode));
    }
}

