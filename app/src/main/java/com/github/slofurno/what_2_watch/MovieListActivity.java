package com.github.slofurno.what_2_watch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MovieListActivity extends ActionBarActivity {
    private ListView lvMovies;
    private ArrayAdapter<Movie> movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<Movie> movies = new ArrayList<Movie>();
        movieAdapter = new ArrayAdapter<Movie>(this,android.R.layout.simple_list_item_multiple_choice, movies);
        lvMovies.setAdapter(movieAdapter);
    }
}
