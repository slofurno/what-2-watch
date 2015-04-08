package com.github.slofurno.what_2_watch.AppState;

import com.github.slofurno.what_2_watch.MovieAggregates.Actor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class ActorManager {
    private HashMap<Integer,Actor> followedActors = new HashMap<>();

    public boolean isFollowed(Actor actor) {
        return followedActors.containsKey(actor.ActorId);
    }

    public void follow(Actor actor) {
        followedActors.put(actor.ActorId, actor);
    }

    public void unfollow(Actor actor) {
        followedActors.remove(actor.ActorId);
    }

    public List<Actor> getFollowed(){
        Collection<Actor> actors = followedActors.values();
        return new ArrayList<Actor>(actors);
    }

}
