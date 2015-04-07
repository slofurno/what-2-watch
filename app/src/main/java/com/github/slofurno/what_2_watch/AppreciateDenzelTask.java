package com.github.slofurno.what_2_watch;

import android.os.AsyncTask;

public class AppreciateDenzelTask extends AsyncTask<Void,Void,Void> {

    @Override protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(5000);
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
