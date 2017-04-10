package com.bsunk.hapanel;

import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.bsunk.hapanel.data.Constants;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.ApplicationComponent;
import com.bsunk.hapanel.di.components.DaggerApplicationComponent;
import com.bsunk.hapanel.di.modules.ApplicationModule;
import com.bsunk.hapanel.services.ConnectionService;

import javax.inject.Inject;

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

        Intent intent = new Intent(this, ConnectionService.class);
        intent.setAction(Constants.ACTION.START_FOREGROUND_ACTION);
        startService(intent);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
