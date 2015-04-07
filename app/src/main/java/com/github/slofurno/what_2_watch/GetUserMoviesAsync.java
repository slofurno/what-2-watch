package com.github.slofurno.what_2_watch;

import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetUserMoviesAsync extends RestApiAsync {

    private UserState mUserState = UserState.getInstance();

    @Override protected URL getUrl() throws MalformedURLException {
        return new URL("http://gdf3.com:555/api/users/" + mUserState.mUserAccount.UserId + "/movies");
    }

    @Override protected void onPostExecute(String result) {

        JsonReader reader = new JsonReader(new StringReader(result));
        reader.setLenient(true);
        Gson gson = new Gson();
        List<Movie> movies = gson.fromJson(reader, new TypeToken<ArrayList<Movie>>(){}.getType());

        OttoBus.getInstance().post(new GetUserMoviesAsyncEvent(movies, mResponseCode));
    }
}

