package com.github.slofurno.what_2_watch.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Fragment;
import android.view.Window;

import com.github.slofurno.what_2_watch.BaseActivity;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.R;
import com.github.slofurno.what_2_watch.Tabs.ActorSearchTab;
import com.github.slofurno.what_2_watch.Tabs.RecommendationsTab;
import com.github.slofurno.what_2_watch.Tabs.TabListener;
import com.github.slofurno.what_2_watch.Tabs.WatchedMoviesTab;

public class MainActivity extends BaseActivity {

    ActionBar.Tab mRecommendTab, mActorSearchTab, mWatchedTab;
    Fragment mRecommendFragment = new RecommendationsTab();
    Fragment mActorSearchFragment = new ActorSearchTab();
    Fragment mWatchedFragment = new WatchedMoviesTab();

    //static public UserAccount mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mRecommendTab = actionBar.newTab().setText("Recommended");
        mActorSearchTab = actionBar.newTab().setText("Actor Search");
        mWatchedTab = actionBar.newTab().setText("Watched");

        mRecommendTab.setTabListener(new TabListener(mRecommendFragment));
        mActorSearchTab.setTabListener(new TabListener(mActorSearchFragment));
        mWatchedTab.setTabListener(new TabListener(mWatchedFragment));

        actionBar.addTab(mRecommendTab);
        actionBar.addTab(mActorSearchTab);
        actionBar.addTab(mWatchedTab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
