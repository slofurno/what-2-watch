package com.github.slofurno.what_2_watch;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slofurno on 4/7/2015.
 */
public class PutUserAccountAsync extends RestApiAsync {

    private UserState mUserState = UserState.getInstance();

    @Override protected URL getUrl() throws MalformedURLException {
        return new URL("http://gdf3.com:555/api/users/create/" + mUserState.mUserAccount.Email);
    }

    @Override protected void onPostExecute(String result) {

        JsonReader reader = new JsonReader(new StringReader(result));
        reader.setLenient(true);
        Gson gson = new Gson();
        UserAccount ua = gson.fromJson(reader, UserAccount.class);

        OttoBus.getInstance().post(new PutUserAccountAsyncEvent(ua, mResponseCode));
    }
}

