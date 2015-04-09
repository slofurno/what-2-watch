package com.github.slofurno.what_2_watch.AppState;

import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;

import javax.inject.Singleton;

/**
 * Created by slofurno on 4/8/2015.
 */
@Singleton
public class AccountManager {

    private UserAccount userAccount = new UserAccount();

    public void setUserAccount(UserAccount ua){
        userAccount=ua;
    }

    public UserAccount getUserAccount(){
        return userAccount;
    }



}
