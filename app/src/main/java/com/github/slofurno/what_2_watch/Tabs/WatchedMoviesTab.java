package com.github.slofurno.what_2_watch.Tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.github.slofurno.what_2_watch.DeleteUserMovieAsync;
import com.github.slofurno.what_2_watch.GetUserMoviesAsync;
import com.github.slofurno.what_2_watch.GetUserMoviesAsyncEvent;
import com.github.slofurno.what_2_watch.GetUserWatchedMoviesAsync;
import com.github.slofurno.what_2_watch.MovieAdapter;
import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.OttoBus;
import com.github.slofurno.what_2_watch.PutUserMovieAsync;
import com.github.slofurno.what_2_watch.PutUserMovieAsyncEvent;
import com.github.slofurno.what_2_watch.R;
import com.github.slofurno.what_2_watch.UserState;
import com.squareup.otto.Subscribe;

import java.util.List;

public class WatchedMoviesTab extends Fragment {

    private ListView listview;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.watchedmovies_layout, container, false);

        listview = (ListView)rootView.findViewById(R.id.listView);

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

        OttoBus.getInstance().register(this);
        GetMovies();

        return rootView;
    }

    public void GetMovies(){
        new GetUserWatchedMoviesAsync().execute();
    }

    @Subscribe
    public void deleteUserMovieResult(PutUserMovieAsyncEvent event) {

        if (event.getResponseCode()==200){

            Movie movie = event.getResult();
            //MovieAdapter adapter = new MovieAdapter(getActivity().getApplicationContext(), movies);
            //listview.setAdapter(adapter);
        }
    }

    @Subscribe
    public void getWatchedUserMoviesResult(GetUserMoviesAsyncEvent event) {

        if (event.getResponseCode()==200){

            List<Movie> movies = event.getResult();

            UserState.selectedMovies.clear();
            //TODO: get this on login
            for (Movie movie : movies){
                UserState.selectedMovies.add(movie.MovieId);
            }

            MovieAdapter adapter = new MovieAdapter(getActivity().getApplicationContext(), movies);
            listview.setAdapter(adapter);
        }
    }

    @Override public void onDestroy() {
        OttoBus.getInstance().unregister(this);
        super.onDestroy();
    }
}
