package com.bsunk.hapanel.ui.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by bryan on 6/2/17.
 */

public class PreferencesUtils {

    public static void keepScreenOn(boolean shouldKeepOn, Activity activity) {
        if(shouldKeepOn) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}
