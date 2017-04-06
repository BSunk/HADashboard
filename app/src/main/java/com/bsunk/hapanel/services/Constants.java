package com.bsunk.hapanel.services;

/**
 * Created by bryan on 4/5/17.
 */

public class Constants {

    public interface ACTION {
        String STARTFOREGROUND_ACTION = "com.bsunk.hpanel.action.startforeground";
        String STOPFOREGROUND_ACTION = "com.bsunk.hpanel.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }
}
