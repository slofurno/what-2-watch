package com.github.slofurno.what_2_watch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.github.slofurno.what_2_watch.AppState.AccountManager;
import com.github.slofurno.what_2_watch.AppState.ActorManager;
import com.github.slofurno.what_2_watch.BaseActivity;
import com.github.slofurno.what_2_watch.MovieApplication;
import com.github.slofurno.what_2_watch.Tasks.AppreciateDenzelTask;
import com.github.slofurno.what_2_watch.Events.AppreciateDenzelTaskEvent;
import com.github.slofurno.what_2_watch.Tasks.GetUserActorsAsync;
import com.github.slofurno.what_2_watch.Events.GetUserActorsAsyncEvent;
import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;
import com.github.slofurno.what_2_watch.AppState.OttoBus;
import com.github.slofurno.what_2_watch.Tasks.PutUserAccountAsync;
import com.github.slofurno.what_2_watch.Events.PutUserAccountAsyncEvent;
import com.github.slofurno.what_2_watch.R;

import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {

    @Inject
    AccountManager accountManager;
    @Inject
    ActorManager actorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MovieApplication) getApplication()).component().inject(this);
        OttoBus.getInstance().register(this);

        SharedPreferences settings = getPreferences(0);

        //TODO: refactor login to take and return a UA,
        UserAccount ua = new UserAccount();

        ua.AccountToken = settings.getString("AccountToken",null);
        ua.Email=settings.getString("Email",null);
        ua.UserId=settings.getInt("UserId",0);

        accountManager.setUserAccount(ua);

        setContentView(R.layout.activity_login);

        if (ua.AccountToken!=null) {
            TryLogin();
        }
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

        findViewById(R.id.email).setVisibility(View.INVISIBLE);
        findViewById(R.id.searchbutton).setVisibility(View.INVISIBLE);
        findViewById(R.id.loginprogress).setVisibility(View.VISIBLE);
        findViewById(R.id.logintext).setVisibility(View.VISIBLE);

        new GetUserActorsAsync().execute();
    }

    @Override protected void onDestroy() {
        OttoBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void getUserActorsResult(GetUserActorsAsyncEvent event) {

        if (event.getResponseCode()==200){

            List<Actor> actors = event.getResult();

            for(Actor actor:actors){
                actorManager.follow(actor);
            }

            new AppreciateDenzelTask().execute();
        }
    }


    @Subscribe
    public void changeActResult(AppreciateDenzelTaskEvent event){

        ChangeActs();
    }


    @Subscribe
    public void putUserAccountResult(PutUserAccountAsyncEvent event){

        UserAccount ua = event.getResult();

        if (event.getResponseCode()==200) {

            SharedPreferences.Editor settings = getPreferences(0).edit();

            accountManager.setUserAccount(ua);

            //TODO:maybe save these settings in accountmanager
            settings.putString("Email", ua.Email);
            settings.putString("AccountToken", ua.AccountToken);
            settings.putInt("UserId", ua.UserId);

            settings.apply();

            TryLogin();
        }
    }

    public void CreateAccount(String email){

        new PutUserAccountAsync(email).execute();
    }

    public void ChangeActs(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void SendEmail(View view) {

        EditText el = (EditText)findViewById(R.id.email);
        String email = el.getText().toString();
        CreateAccount(email);
    }
}
