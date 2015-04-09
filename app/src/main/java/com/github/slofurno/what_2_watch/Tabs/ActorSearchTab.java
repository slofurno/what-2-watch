package com.github.slofurno.what_2_watch.Tabs;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.github.slofurno.what_2_watch.Adapters.ActorAdapter;
import com.github.slofurno.what_2_watch.AppState.ActorManager;
import com.github.slofurno.what_2_watch.BaseActivity;
import com.github.slofurno.what_2_watch.BaseFragment;
import com.github.slofurno.what_2_watch.MovieApplication;
import com.github.slofurno.what_2_watch.Tasks.GetActorsAsync;
import com.github.slofurno.what_2_watch.Events.GetActorsAsyncEvent;
import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.Tasks.PatchUserActorsAsync;
import com.github.slofurno.what_2_watch.R;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

public class ActorSearchTab extends BaseFragment {
    @Inject
    ActorManager actorManager;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.actorsearch_layout, container, false);
        ((MovieApplication)((BaseActivity)getActivity()).getApplication()).component().inject(this);
        listview = (ListView)rootView.findViewById(R.id.actorlist);

        ActorAdapter adapter = new ActorAdapter(getActivity().getApplicationContext(), actorManager.getFollowed(), actorManager);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                Actor actor = (Actor)listview.getItemAtPosition(position);
                CheckedTextView cv = (CheckedTextView)view;

                if (actorManager.isFollowed(actor)){
                    actorManager.unfollow(actor);
                    cv.setChecked(false);
                }
                else {
                    actorManager.follow(actor);
                    /*
                    if (!UserState.addedActors.contains(actor.ActorId)){
                        UserState.myActors.add(actor);
                        UserState.addedActors.add(actor.ActorId);
                    }
                    */
                    cv.setChecked(true);
                }

                new PatchUserActorsAsync(actor.ActorId).execute();
            }

        });

       // actually setting the checkmode on this appears to fck everything up

        Button button = (Button)rootView.findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText actorname = (EditText) rootView.findViewById(R.id.actorname);
                String name = actorname.getText().toString();
                ((EditText) rootView.findViewById(R.id.actorname)).setText("");
                GetActors(name);
            }
        });

        OttoBus.getInstance().register(this);
        return rootView;
    }

   protected void GetActors(String name){
       new GetActorsAsync(name).execute();
   }

    @Subscribe
    public void putUserAccountResult(GetActorsAsyncEvent event){

        List<Actor> actors = event.getResult();

        if (event.getResponseCode()==200) {

            ActorAdapter adapter = new ActorAdapter(getActivity().getApplicationContext(), actors, actorManager);
            listview.setAdapter(adapter);
        }
    }
}