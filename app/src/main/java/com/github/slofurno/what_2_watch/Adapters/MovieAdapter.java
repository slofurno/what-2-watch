package com.github.slofurno.what_2_watch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.github.slofurno.what_2_watch.AppState.ActorManager;
import com.github.slofurno.what_2_watch.AppState.MovieManager;
import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.R;

import java.util.List;

import javax.inject.Inject;

public class MovieAdapter extends ArrayAdapter<Movie> {

    @Inject
    MovieManager movieManager;
    LayoutInflater mInflater;
    List<Movie> mMovies;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.actor_view, movies);
        mInflater= (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMovies=movies;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //simple_list_item_multiple_choice
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.actor_view, parent, false);
        }

        Movie movie = getItem(position);
        Integer movieId = new Integer(movie.MovieId);

        CheckedTextView checktextview = (CheckedTextView)view.findViewById(R.id.actor1);
        // CheckBox isFavorited = (CheckBox)view.findViewById(R.id.isFavorited);

        checktextview.setText(movie.toString());
        if (movieManager.hasWatched(movie)){
            checktextview.setChecked(true);
        }
        else{
            checktextview.setChecked(false);
        }

        return view;
    }
}
