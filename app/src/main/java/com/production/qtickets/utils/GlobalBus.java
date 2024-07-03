package com.production.qtickets.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Harsh on 4/6/2018.
 */

public class GlobalBus {

    private static EventBus sBus;
    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }

}
