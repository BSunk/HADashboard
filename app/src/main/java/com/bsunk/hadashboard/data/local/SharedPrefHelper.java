package com.bsunk.hadashboard.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by Bharat on 3/5/2017.
 */

public class SharedPrefHelper {

    private static final String PREF_FILE_NAME = "HADashboard_pref_file";
    private static final String PREF_KEY_PW = "PREF_KEY_PW";
    private static final String PREF_KEY_IP = "PREF_KEY_IP";
    private static final String PREF_KEY_PORT = "PREF_KEY_PORT";
    private static final String PREF_KEY_FIRST_LAUNCH = "PREF_KEY_FIRST_LAUNCH";

    private SharedPreferences mSharedPreferences;

    @Inject
    public SharedPrefHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void putIP(String ip) {
        mSharedPreferences.edit().putString(PREF_KEY_IP, ip).apply();
    }

    public void putPort(String port) {
        mSharedPreferences.edit().putString(PREF_KEY_PORT, port).apply();
    }

    public void putPW(String pw) {
        mSharedPreferences.edit().putString(PREF_KEY_PW, pw).apply();
    }

    public void putFirstLaunch(boolean isFirst) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_FIRST_LAUNCH, isFirst).apply();
    }

    public String getIP() {
        String ip = mSharedPreferences.getString(PREF_KEY_IP, null);
        if(ip == null) return null;
        return ip;
    }

    public String getPort() {
        String port = mSharedPreferences.getString(PREF_KEY_PORT, null);
        if(port == null) return null;
        return port;
    }

    public String getPW() {
        String pw = mSharedPreferences.getString(PREF_KEY_PW, null);
        if(pw == null) return null;
        return pw;
    }
    public Boolean isFirstLaunch() {
        return mSharedPreferences.getBoolean(PREF_KEY_FIRST_LAUNCH, false);
    }

}
