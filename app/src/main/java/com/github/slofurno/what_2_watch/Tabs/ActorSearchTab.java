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
import android.widget.EditText;
import android.widget.ListView;

import com.github.slofurno.what_2_watch.Activities.LoginActivity;
import com.github.slofurno.what_2_watch.Activities.MainActivity;
import com.github.slofurno.what_2_watch.ActorAdapter;
import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.R;
import com.github.slofurno.what_2_watch.UserState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
                new AsyncTask<String, Void, String>(){
                    @Override
                    protected String doInBackground(String... urls) {

                        try {
                            postUrl(urls[0]);
                            return "ok?";
                        } catch (IOException e) {
                            return "Unable to retrieve web page. URL may be invalid.";
                        }
                    }

                }.execute("http://gdf3.com:555/api/users/" + LoginActivity.mAccount.UserId + "/actors/"+actor.ActorId);

            }

        });













       // actually setting the checkmode on this appears to fck everything up

        Button button = (Button)rootView.findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText actorname = (EditText) rootView.findViewById(R.id.actorname);
                String name = actorname.getText().toString();


                GetActors("http://gdf3.com:555/api/actors/"+name);
            }
        });
        //adapter = new ArrayAdapter<Movie>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,movies);

        // listview.setAdapter(adapter);
        return rootView;
    }

    public AsyncTask GetActors(String url){

        AsyncTask task = new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... urls) {

                try {
                    return downloadUrl(urls[0]);
                } catch (IOException e) {
                    return "Unable to retrieve web page. URL may be invalid.";
                }
            }

            @Override
            protected void onPostExecute(final String result) {

                JsonReader reader = new JsonReader(new StringReader(result));
                reader.setLenient(true);
                Gson gson =new Gson();
                List<Actor> actors = gson.fromJson(reader,new TypeToken<List<Actor>>(){}.getType());

                if (getActivity()==null){
                    return;
                }

                ActorAdapter adapter = new ActorAdapter(getActivity().getApplicationContext(), actors);

                //ArrayAdapter<Actor> adapter = new ArrayAdapter<Actor>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1, actors);
                listview.setAdapter(adapter);
/*
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            final int position, long id) {

                        Actor actor = (Actor)listview.getItemAtPosition(position);
                        CheckedTextView cv = (CheckedTextView)view;

                        if (UserState.selectedActors.contains(actor.ActorId)){
                            UserState.selectedActors.remove(actor.ActorId);
                            cv.setChecked(false);
                            //listview.setItemChecked(position,false);
                        }
                        else {
                            UserState.selectedActors.add(actor.ActorId);
                            cv.setChecked(true);
                            //listview.setItemChecked(position,true);
                        }
                        new AsyncTask<String, Void, String>(){
                            @Override
                            protected String doInBackground(String... urls) {

                                try {
                                    postUrl(urls[0]);
                                    return "ok?";
                                } catch (IOException e) {
                                    return "Unable to retrieve web page. URL may be invalid.";
                                }
                            }

                        }.execute("http://gdf3.com:555/api/users/" + LoginActivity.mAccount.UserId + "/actors/"+actor.ActorId);

                    }

                }); */


            }
        }.execute(url);

        return task;

    }

    private Void postUrl(String myurl)throws IOException {

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", LoginActivity.mAccount.AccountToken);
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
        }
        finally{

        }
        return null;
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", LoginActivity.mAccount.AccountToken);
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("httpreq", "The response is: " + response);

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String readIt(InputStream is) throws IOException, UnsupportedEncodingException {

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        return response.toString();

    }

}