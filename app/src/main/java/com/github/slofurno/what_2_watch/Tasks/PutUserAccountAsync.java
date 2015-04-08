package com.github.slofurno.what_2_watch.Tasks;

import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.Events.PutUserAccountAsyncEvent;
import com.github.slofurno.what_2_watch.AppState.UserState;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

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

