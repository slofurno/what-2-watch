package com.github.slofurno.what_2_watch;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by slofurno on 4/8/2015.
 */
public abstract class BaseFragment extends Fragment {

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Perform injection so that when this call returns all dependencies will be available for use.
        ((MovieApplication)((BaseActivity)getActivity()).getApplication()).component().inject(this);

        //((MovieApplication) getApplication()).component().inject(this);
    }
}
