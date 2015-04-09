package com.github.slofurno.what_2_watch.Tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.github.slofurno.what_2_watch.AppState.MovieManager;
import com.github.slofurno.what_2_watch.BaseActivity;
import com.github.slofurno.what_2_watch.BaseFragment;
import com.github.slofurno.what_2_watch.MovieApplication;
import com.github.slofurno.what_2_watch.Tasks.DeleteUserMovieAsync;
import com.github.slofurno.what_2_watch.Events.GetUserMoviesAsyncEvent;
import com.github.slofurno.what_2_watch.Tasks.GetUserWatchedMoviesAsync;
import com.github.slofurno.what_2_watch.Adapters.MovieAdapter;
import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.Tasks.PutUserMovieAsync;
import com.github.slofurno.what_2_watch.Events.PutUserMovieAsyncEvent;
import com.github.slofurno.what_2_watch.R;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

public class WatchedMoviesTab extends BaseFragment {

    @Inject
    MovieManager movieManager;
    private ListView listview;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MovieApplication)((BaseActivity)getActivity()).getApplication()).component().inject(this);
        OttoBus.getInstance().register(this);

        rootView = inflater.inflate(R.layout.watchedmovies_layout, container, false);
        listview = (ListView)rootView.findViewById(R.id.listView);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                Movie movie = (Movie)listview.getItemAtPosition(position);
                CheckedTextView cv = (CheckedTextView)view;

                if (movieManager.hasWatched(movie)){
                    movieManager.unwatch(movie);
                    new DeleteUserMovieAsync(movie.MovieId).execute();
                    //UserState.myActors.remove(actor);
                    cv.setChecked(false);
                    //listview.setItemChecked(position,false);
                }
                else {
                    movieManager.watch(movie);
                    new PutUserMovieAsync(movie.MovieId).execute();
                    cv.setChecked(true);
                    //listview.setItemChecked(position,true);
                }
            }
        });



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

            //TODO: get this on login
            for (Movie movie : movies){
               movieManager.watch(movie);
            }

            MovieAdapter adapter = new MovieAdapter(getActivity().getApplicationContext(), movies, movieManager);
            listview.setAdapter(adapter);
        }
    }

    @Override public void onDestroy() {
        OttoBus.getInstance().unregister(this);
        super.onDestroy();
    }
}
