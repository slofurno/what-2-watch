package com.github.slofurno.what_2_watch.Tabs;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ListView;

import com.github.slofurno.what_2_watch.Activities.LoginActivity;
import com.github.slofurno.what_2_watch.Activities.MainActivity;
import com.github.slofurno.what_2_watch.ActorAdapter;
import com.github.slofurno.what_2_watch.GetActorsAsync;
import com.github.slofurno.what_2_watch.GetActorsAsyncEvent;
import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.OttoBus;
import com.github.slofurno.what_2_watch.PatchUserActorsAsync;
import com.github.slofurno.what_2_watch.PutUserAccountAsyncEvent;
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
import java.util.List;

public class ActorSearchTab extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayAdapter<Actor> adapter;
    private Context context;
    private View rootView;
    private ListView listview;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ActorSearchTab newInstance(int sectionNumber) {

        ActorSearchTab fragment = new ActorSearchTab();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onDestroy() {
        OttoBus.getInstance().unregister(this);
        super.onDestroy();
    }

    public ActorSearchTab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.actorsearch_layout, container, false);

        listview = (ListView)rootView.findViewById(R.id.actorlist);

        ActorAdapter adapter = new ActorAdapter(getActivity().getApplicationContext(), UserState.myActors);

        //ArrayAdapter<Actor> adapter = new ArrayAdapter<Actor>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, actors);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                Actor actor = (Actor)listview.getItemAtPosition(position);
                CheckedTextView cv = (CheckedTextView)view;

                if (UserState.selectedActors.contains(actor.ActorId)){
                    UserState.selectedActors.remove(actor.ActorId);
                    //UserState.myActors.remove(actor);
                    cv.setChecked(false);
                    //listview.setItemChecked(position,false);
                }
                else {
                    UserState.selectedActors.add(actor.ActorId);
                    if (!UserState.addedActors.contains(actor.ActorId)){
                        UserState.myActors.add(actor);
                        UserState.addedActors.add(actor.ActorId);
                    }
                    cv.setChecked(true);
                    //listview.setItemChecked(position,true);
                }

                new PatchUserActorsAsync(actor.ActorId).execute();

            }

        });

       // actually setting the checkmode on this appears to fck everything up

        Button button = (Button)rootView.findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText actorname = (EditText) rootView.findViewById(R.id.actorname);
                String name = actorname.getText().toString();
                GetActors(name);
            }
        });
        //adapter = new ArrayAdapter<Movie>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,movies);

        OttoBus.getInstance().register(this);

        // listview.setAdapter(adapter);
        return rootView;
    }

   protected void GetActors(String name){
       new GetActorsAsync(name).execute();
   }

    @Subscribe
    public void putUserAccountResult(GetActorsAsyncEvent event){

        List<Actor> actors = event.getResult();

        if (event.getResponseCode()==200) {

            ActorAdapter adapter = new ActorAdapter(getActivity().getApplicationContext(), actors);
            listview.setAdapter(adapter);
        }

    }


}