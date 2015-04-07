package com.github.slofurno.what_2_watch;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetActorsAsync extends RestApiAsync {

    protected String mSearchTerm;

    public GetActorsAsync(String term){
        mSearchTerm=term;
    }

    private UserState mUserState = UserState.getInstance();

    @Override protected URL getUrl() throws MalformedURLException {
        //UserState userState = UserState.getInstance();
        //UserAccount ua = userState.mUserAccount;
        return new URL("http://gdf3.com:555/api/actors/"+mSearchTerm);
    }

    @Override protected void onPostExecute(String result) {

        JsonReader reader = new JsonReader(new StringReader(result));
        reader.setLenient(true);
        Gson gson = new Gson();
        List<Actor> actors = gson.fromJson(reader, new TypeToken<ArrayList<Actor>>(){}.getType());

        OttoBus.getInstance().post(new GetActorsAsyncEvent(actors, mResponseCode));
    }
}
