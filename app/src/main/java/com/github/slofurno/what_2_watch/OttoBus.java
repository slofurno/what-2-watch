package com.github.slofurno.what_2_watch;

import com.squareup.otto.Bus;

import java.util.List;

/**
 * Created by slofurno on 4/7/2015.
 */
public class OttoBus {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}

