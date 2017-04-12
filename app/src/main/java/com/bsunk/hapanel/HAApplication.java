package com.bsunk.hapanel;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.bsunk.hapanel.di.components.ApplicationComponent;
import com.bsunk.hapanel.di.components.DaggerApplicationComponent;
import com.bsunk.hapanel.di.modules.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Bryan on 3/4/2017.
 */

public class HAApplication extends MultiDexApplication {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static HAApplication get(Context context) {
        return (HAApplication) context.getApplicationContext();
    }

}
