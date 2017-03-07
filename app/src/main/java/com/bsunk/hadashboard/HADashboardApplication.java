package com.bsunk.hadashboard;

import android.support.multidex.MultiDexApplication;

import com.bsunk.hadashboard.di.components.ApplicationComponent;
import com.bsunk.hadashboard.di.components.DaggerApplicationComponent;
import com.bsunk.hadashboard.di.modules.ApplicationModule;

import timber.log.Timber;

/**
 * Created by Bryan on 3/4/2017.
 */

public class HADashboardApplication extends MultiDexApplication {

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

}
