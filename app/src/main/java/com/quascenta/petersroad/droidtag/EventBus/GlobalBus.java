package com.quascenta.petersroad.droidtag.EventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by AKSHAY on 2/10/2017.
 */


public class GlobalBus {
    private static EventBus sBus;

    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }
}

