package com.github.slofurno.what_2_watch;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;
import com.github.slofurno.what_2_watch.MovieAggregates.UserAccount;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by slofurno on 4/6/2015.
 */
public class UserState {

    public static HashSet<Integer> selectedActors = new HashSet<>();
    public static HashSet<Integer> addedActors = new HashSet<>();
    public static List<Actor> myActors = new ArrayList<>();
    public UserAccount mUserAccount = new UserAccount();

    private static final UserState USER_STATE = new UserState();

    public static UserState getInstance() {
        return USER_STATE;
    }
}
