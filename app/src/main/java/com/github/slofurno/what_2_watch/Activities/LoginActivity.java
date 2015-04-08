package com.github.slofurno.what_2_watch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
import com.github.slofurno.what_2_watch.AppState.UserState;

import com.squareup.otto.Subscribe;

import java.util.List;

public class LoginActivity extends Activity {

    private String AccountToken;
    private int UserId;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OttoBus.getInstance().register(this);

        SharedPreferences settings = getPreferences(0);
        UserState userState = UserState.getInstance();
        //TODO: refactor login to take and return a UA,
        UserAccount ua = userState.mUserAccount;

        ua.AccountToken = settings.getString("AccountToken",null);
        ua.Email=settings.getString("Email",null);
        ua.UserId=settings.getInt("UserId",0);

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

            for(int i = 0; i < actors.size();i++){
                UserState.selectedActors.add(actors.get(i).ActorId);
                UserState.addedActors.add(actors.get(i).ActorId);
                UserState.myActors.add(actors.get(i));
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

            UserState userState = UserState.getInstance();
            UserAccount mua = userState.mUserAccount;

            mua.UserId=ua.UserId;
            mua.Email=ua.Email;
            mua.AccountToken=ua.AccountToken;

            settings.putString("Email", ua.Email);
            settings.putString("AccountToken", ua.AccountToken);
            settings.putInt("UserId", ua.UserId);

            settings.apply();

            TryLogin();
        }
    }

    public void CreateAccount(String email){

        UserState userState = UserState.getInstance();
        userState.mUserAccount.Email=email;
        new PutUserAccountAsync().execute();
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
}
