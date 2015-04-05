package com.github.slofurno.what_2_watch;

import com.loopj.android.http.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by slofurno on 4/4/2015.
 */
public class MovieClient {
    private static final String API_BASE_URL = "http://gdf3.com:555/api/";
    private AsyncHttpClient client;

    public MovieClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getMovies(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("movies/");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



}
