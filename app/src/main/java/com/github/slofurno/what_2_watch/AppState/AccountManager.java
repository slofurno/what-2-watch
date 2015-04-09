package com.github.slofurno.what_2_watch.AppState;

import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;

import javax.inject.Singleton;

/**
 * Created by slofurno on 4/8/2015.
 */

public class AccountManager {

    private AccountManager(){}

    private static class SingletonHolder {
        private static final AccountManager INSTANCE = new AccountManager();
    }

    public static AccountManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private UserAccount userAccount = new UserAccount();

    public void setUserAccount(UserAccount ua){
        userAccount=ua;
    }

    public UserAccount getUserAccount(){
        return userAccount;
    }
}


