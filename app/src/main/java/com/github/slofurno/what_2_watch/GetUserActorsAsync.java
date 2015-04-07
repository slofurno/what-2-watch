package com.github.slofurno.what_2_watch;

import android.os.AsyncTask;

import com.github.slofurno.what_2_watch.Activities.LoginActivity;
import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slofurno on 4/7/2015.
 */

public class GetUserActorsAsync extends RestApiAsync {

    private UserState mUserState = UserState.getInstance();

    @Override protected URL getUrl() throws MalformedURLException {
        return new URL("http://gdf3.com:555/api/users/"+mUserState.mUserAccount.UserId+"/actors");
    }

    @Override protected void onPostExecute(String result) {

        JsonReader reader = new JsonReader(new StringReader(result));
        reader.setLenient(true);
        Gson gson = new Gson();
        List<Actor> actors = gson.fromJson(reader, new TypeToken<ArrayList<Actor>>(){}.getType());

        OttoBus.getInstance().post(new GetUserActorsAsyncEvent(actors, mResponseCode));
    }
}

