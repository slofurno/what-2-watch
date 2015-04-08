package com.github.slofurno.what_2_watch.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.R;
import com.github.slofurno.what_2_watch.AppState.UserState;

import java.util.List;

/**
 * Created by slofurno on 4/6/2015.
 */
public class ActorAdapter extends ArrayAdapter<Actor> {

    LayoutInflater mInflater;
    List<Actor> mActors;

    public ActorAdapter(Context context, List<Actor> actors) {
        super(context, R.layout.actor_view, actors);
        mInflater= (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mActors=actors;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //simple_list_item_multiple_choice
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.actor_view, parent, false);
        }

        Actor actor = getItem(position);
        Integer actorid = new Integer(actor.ActorId);

        CheckedTextView checktextview = (CheckedTextView)view.findViewById(R.id.actor1);
       // CheckBox isFavorited = (CheckBox)view.findViewById(R.id.isFavorited);

        checktextview.setText(actor.First + " " + actor.Last);
        if (UserState.selectedActors.contains(actorid)){
            checktextview.setChecked(true);
        }
        else{
            checktextview.setChecked(false);
        }

        return view;

    }
}


