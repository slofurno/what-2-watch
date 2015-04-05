package com.github.slofurno.what_2_watch.Tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.slofurno.what_2_watch.R;

public class ActorSearchTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.actorsearch_layout, container, false);
    }
}
