package com.github.slofurno.what_2_watch.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.MovieAggregates.MovieClient;
import com.github.slofurno.what_2_watch.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends ActionBarActivity implements ActionBar.TabListener {
    private ListView lvMovies;
    private ArrayAdapter<Movie> movieAdapter;//todo: create a custom movielistadapter with custom layout
    private MovieClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<Movie> movies = new ArrayList<Movie>();
        movieAdapter = new ArrayAdapter<Movie>(this,android.R.layout.simple_list_item_multiple_choice, movies);
        lvMovies.setAdapter(movieAdapter);
    }

    private void fetchMovies() {
        client = new MovieClient();
        client.getMovies("american", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    // Get the docs json array
                    String json = response.toString();
                    Gson gson =new Gson();
                    List<Movie> movies = gson.fromJson(json,new TypeToken<List<Movie>>(){}.getType());

                    // Parse json array into array of model objects
                    // Remove all books from the adapter
                    movieAdapter.clear();
                    // Load model objects into the adapter
                    for (Movie movie : movies) {
                        movieAdapter.add(movie); // add book through the adapter
                    }
                    movieAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
