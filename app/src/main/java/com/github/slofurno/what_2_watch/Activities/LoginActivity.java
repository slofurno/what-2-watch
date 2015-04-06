package com.github.slofurno.what_2_watch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.R;
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

public class LoginActivity extends Activity {

    public static UserAccount mAccount;
    private String AccountToken;
    private int UserId;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getPreferences(0);

        mAccount=new UserAccount();

        mAccount.AccountToken=settings.getString("AccountToken",null);
        mAccount.Email=settings.getString("Email",null);
        mAccount.UserId=settings.getInt("UserId",0);

        if (mAccount.AccountToken!=null) {
            TryLogin();
        }

        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void TryLogin(){

        new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... urls) {

                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", mAccount.AccountToken);
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    int response = conn.getResponseCode();

                    if (response==200){
                        ChangeActs();
                    }


                } catch (IOException e) {

                }

                return null;
            }


        }.execute("http://gdf3.com:555/api/users/"+mAccount.UserId+"/test");
    }

    public void CreateAccount(String email){

        new AsyncTask<String, Void, String>(){
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

                if (result.length()>1) {

                    JsonReader reader = new JsonReader(new StringReader(result));
                    reader.setLenient(true);
                    Gson gson = new Gson();
                    UserAccount ua = gson.fromJson(reader, UserAccount.class);

                    SharedPreferences.Editor settings = getPreferences(0).edit();

                    mAccount = ua;
                    settings.putString("Email", ua.Email);
                    settings.putString("AccountToken", ua.AccountToken);
                    settings.putInt("UserId", ua.UserId);

                    settings.apply();
                }

                TryLogin();

            }
        }.execute("http://gdf3.com:555/api/users/create/" + email);

    }

    public void ChangeActs(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Email",Email);
        intent.putExtra("AccountToken",AccountToken);
        intent.putExtra("UserId",UserId);
        startActivity(intent);
    }

    public void SendEmail(View view) {

        EditText el = (EditText)findViewById(R.id.email);
        String email = el.getText().toString();

        CreateAccount(email);

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
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();

            if (response!=200){
                return "";
            }

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);

            Log.d("url", myurl);
            Log.d("content", contentAsString);
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
