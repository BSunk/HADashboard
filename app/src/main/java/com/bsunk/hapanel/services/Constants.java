package com.bsunk.hapanel.services;

/**
 * Created by bryan on 4/5/17.
 */

public class Constants {

    public interface ACTION {
        public static String STARTFOREGROUND_ACTION = "com.bsunk.hpanel.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.bsunk.hpanel.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
