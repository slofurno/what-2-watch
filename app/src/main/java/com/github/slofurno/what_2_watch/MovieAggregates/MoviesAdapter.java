package com.github.slofurno.what_2_watch.MovieAggregates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.slofurno.what_2_watch.MovieAggregates.Movie;
import com.github.slofurno.what_2_watch.R;

import java.util.ArrayList;

/**
 * Created by slofurno on 4/4/2015.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {
    public MoviesAdapter(Context context, ArrayList<Movie> users) {
        super(context, 0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// Get the data item for this position
        Movie movie = getItem(position);
// Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }
// Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.movieName);
        TextView year = (TextView) convertView.findViewById(R.id.movieYear);
        TextView rating = (TextView) convertView.findViewById(R.id.movieRating);
// Populate the data into the template view using the data object
        name.setText(movie.title);
        year.setText(movie.year);
        rating.setText(movie.year);
// Return the completed view to render on screen
        return convertView;
    }
}
