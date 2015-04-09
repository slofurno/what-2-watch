package com.github.slofurno.what_2_watch.Tasks;

import android.os.AsyncTask;

import com.github.slofurno.what_2_watch.Events.AppreciateDenzelTaskEvent;
import com.github.slofurno.what_2_watch.AppState.OttoBus;

public class AppreciateDenzelTask extends AsyncTask<Void,Void,Void> {

    @Override protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(500);
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override protected void onPostExecute(Void result) {
        OttoBus.getInstance().post(new AppreciateDenzelTaskEvent());
    }

}
