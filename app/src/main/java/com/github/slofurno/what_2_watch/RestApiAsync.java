package com.github.slofurno.what_2_watch;

import android.os.AsyncTask;

import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by slofurno on 4/7/2015.
 */
public abstract class RestApiAsync extends AsyncTask<Void, Void, String> {

    protected UserState mUserState = UserState.getInstance();
    protected int mResponseCode;
    protected abstract URL getUrl() throws MalformedURLException;

    @Override protected String doInBackground(Void... params) {
        InputStream is = null;
        UserState userState = UserState.getInstance();
        UserAccount ua = userState.mUserAccount;

        try {
            URL url = getUrl();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", ua.AccountToken);
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            mResponseCode = conn.getResponseCode();

            is = conn.getInputStream();

            return readToEnd(is);

        } catch (IOException e) {
            return null;
        }
    }

    protected String readToEnd(InputStream is) throws IOException, UnsupportedEncodingException {

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
