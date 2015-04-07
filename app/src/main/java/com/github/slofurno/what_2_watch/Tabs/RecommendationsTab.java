package com.github.slofurno.what_2_watch.Tabs;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.github.slofurno.what_2_watch.Activities.LoginActivity;
import com.github.slofurno.what_2_watch.Activities.MainActivity;
import com.github.slofurno.what_2_watch.DeleteUserMovieAsync;
import com.github.slofurno.what_2_watch.GetUserMoviesAsync;
import com.github.slofurno.what_2_watch.GetUserMoviesAsyncEvent;
import com.github.slofurno.what_2_watch.MovieAdapter;
import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.OttoBus;
import com.github.slofurno.what_2_watch.PatchUserActorsAsync;
import com.github.slofurno.what_2_watch.PutUserMovieAsync;
import com.github.slofurno.what_2_watch.PutUserMovieAsyncEvent;
import com.github.slofurno.what_2_watch.R;
import com.github.slofurno.what_2_watch.UserState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.squareup.otto.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecommendationsTab extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listview;
    private View rootView;
    private ArrayAdapter<Movie> adapter;
    private List<Movie> movies;
    private Context context;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RecommendationsTab newInstance(int sectionNumber) {
        RecommendationsTab fragment = new RecommendationsTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recommendations_layout, container, false);

        listview = (ListView)rootView.findViewById(R.id.listView);

        Button button = (Button)rootView.findViewById(R.id.fetchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                UserState userState = UserState.getInstance();
                UserAccount ua = userState.mUserAccount;
                GetMovies();
            }
        });
        //adapter = new ArrayAdapter<Movie>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,movies);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                Movie movie = (Movie)listview.getItemAtPosition(position);
                CheckedTextView cv = (CheckedTextView)view;

                if (UserState.selectedMovies.contains(movie.MovieId)){
                    UserState.selectedMovies.remove(movie.MovieId);
                    new DeleteUserMovieAsync(movie.MovieId).execute();
                    //UserState.myActors.remove(actor);
                    cv.setChecked(false);
                    //listview.setItemChecked(position,false);
                }
                else {
                    UserState.selectedMovies.add(movie.MovieId);
                    new PutUserMovieAsync(movie.MovieId).execute();
                    cv.setChecked(true);
                    //listview.setItemChecked(position,true);
                }
            }
        });

        UserState userState = UserState.getInstance();
        UserAccount ua = userState.mUserAccount;
        // listview.setAdapter(adapter);
        GetMovies();
        OttoBus.getInstance().register(this);

        return rootView;
    }

    public void GetMovies(){
        new GetUserMoviesAsync().execute();
    }

    @Subscribe
    public void putUserMovieResult(PutUserMovieAsyncEvent event) {

        if (event.getResponseCode()==200){
            Movie movie = event.getResult();
            //MovieAdapter adapter = new MovieAdapter(getActivity().getApplicationContext(), movies);
            //listview.setAdapter(adapter);
        }
    }

    @Subscribe
    public void getUserActorsResult(GetUserMoviesAsyncEvent event) {

        if (event.getResponseCode()==200){

            List<Movie> movies = event.getResult();
            MovieAdapter adapter = new MovieAdapter(getActivity().getApplicationContext(), movies);
            listview.setAdapter(adapter);
        }
    }

    @Override public void onDestroy() {
        OttoBus.getInstance().unregister(this);
        super.onDestroy();
    }
}
