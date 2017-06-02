package com.bsunk.hapanel.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_FILE_NAME;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_FIRST_LAUNCH;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_HOME_SORTED;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_IP;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_LAT;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_LOCATION_NAME;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_LONG;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_PORT;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_PW;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_TIME_ZONE;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_TOOLBAR_TITLE;
import static com.bsunk.hapanel.data.Constants.SHARED_PREFS.PREF_KEY_VERSION;

/**
 * Created by Bharat on 3/5/2017.
 */

public class SharedPrefHelper {


    private SharedPreferences mSharedPreferences;
    private SharedPreferences mSharedPreferencesManager;

    @Inject
    public SharedPrefHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferencesManager = PreferenceManager.getDefaultSharedPreferences(context);
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

    public void putFirstLaunchCompleted() {
        mSharedPreferences.edit().putBoolean(PREF_KEY_FIRST_LAUNCH, true).apply();
    }

    public String getIP() {
        return mSharedPreferences.getString(PREF_KEY_IP, null);
    }

    public String getPort() {
        return mSharedPreferences.getString(PREF_KEY_PORT, null);
    }

    public String getPW() {
        return mSharedPreferences.getString(PREF_KEY_PW, null);
    }

    public Boolean getWelcomeScreenLaunched() {
        return mSharedPreferences.getBoolean(PREF_KEY_FIRST_LAUNCH, false);
    }

    public void putLocationName(String data) {
        mSharedPreferences.edit().putString(PREF_KEY_LOCATION_NAME, data).apply();
    }

    public void putToolbarTitle(String data) {
        mSharedPreferences.edit().putString(PREF_KEY_TOOLBAR_TITLE, data).apply();
    }

    public void putTimeZone(String data) {
        mSharedPreferences.edit().putString(PREF_KEY_TIME_ZONE, data).apply();
    }

    public void putLong(String data) {
        mSharedPreferences.edit().putString(PREF_KEY_LONG, data).apply();
    }

    public void putLat(String data) {
        mSharedPreferences.edit().putString(PREF_KEY_LAT, data).apply();
    }

    public void putVersion(String data) {
        mSharedPreferences.edit().putString(PREF_KEY_VERSION, data).apply();
    }

    public String getLocationName() {
        return mSharedPreferences.getString(PREF_KEY_LOCATION_NAME, null);
    }

    public String getToolbarTitle() {
        return mSharedPreferences.getString(PREF_KEY_TOOLBAR_TITLE, null);
    }

    public void putHomeDevicesList(String data) {
        mSharedPreferences.edit().putString(PREF_KEY_HOME_SORTED, data).apply();
    }

    public String getHomeDevicesList() {
        return mSharedPreferences.getString(PREF_KEY_HOME_SORTED, "");
    }

    public String getLongitude() {
        return mSharedPreferences.getString(PREF_KEY_LONG, null);
    }

    public String getLatitude() {
        return mSharedPreferences.getString(PREF_KEY_LAT, null);
    }

    public String getTimeZone() {
        return mSharedPreferences.getString(PREF_KEY_TIME_ZONE, null);
    }

    public String getHAVersion() {
        return mSharedPreferences.getString(PREF_KEY_VERSION, null);
    }

    public boolean getScreenOn() {
        return mSharedPreferencesManager.getBoolean("pref_screen_on", false);
    }

}
