package com.bsunk.hapanel.data;

/**
 * Created by bryan on 4/5/17.
 */

public class Constants {

    public interface ACTION {
        String START_FOREGROUND_ACTION = "com.bsunk.hpanel.action.startforeground";
        String STOP_FOREGROUND_ACTION = "com.bsunk.hpanel.action.stopforeground";
        String RETRY_CONNECTION_ACTION = "com.bsunk.hpanel.action.retry";
    }

    public interface NOTIFICATION_ID {
        int FOREGROUND_SERVICE = 101;
    }

    public interface SHARED_PREFS {
        String PREF_FILE_NAME = "HADashboard_pref_file";
        String PREF_KEY_PW = "PREF_KEY_PW";
        String PREF_KEY_IP = "PREF_KEY_IP";
        String PREF_KEY_PORT = "PREF_KEY_PORT";
        String PREF_KEY_FIRST_LAUNCH = "PREF_KEY_FIRST_LAUNCH";

        String PREF_KEY_LOCATION_NAME = "PREF_KEY_LOCATION_NAME";
        String PREF_KEY_TIME_ZONE = "PREF_KEY_TIME_ZONE";
        String PREF_KEY_LAT = "PREF_KEY_LAT";
        String PREF_KEY_LONG = "PREF_KEY_LONG";
        String PREF_KEY_VERSION = "PREF_KEY_VERSION";
    }

    public interface WEB_SOCKET_EVENTS {
        int EVENT_CONNECTING = 0;
        int EVENT_CONNECTED = 1;
        int EVENT_AUTH_FAILED = 2;
        int EVENT_FAILED = 3;
        int EVENT_CLOSED = 4;
        int EVENT_NO_SERVER = 5;

        int NORMAL_CLOSURE_STATUS = 1000;

        String TYPE_AUTH_OK = "auth_ok";
        String TYPE_AUTH_REQUIRED = "auth_required";
        String TYPE_AUTH_INVALID = "auth_invalid";
        String TYPE_RESULT = "result";
        String TYPE_EVENT = "event";
    }

    public interface DEVICE_TYPE {
        String SENSOR_TYPE = "sensor";
        String LIGHT_TYPE = "light";
        String MEDIA_PLAYER_TYPE = "media_player";
    }

}
