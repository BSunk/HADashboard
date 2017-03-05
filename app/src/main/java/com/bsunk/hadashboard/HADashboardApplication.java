package com.bsunk.hadashboard;

import android.support.multidex.MultiDexApplication;

import com.bsunk.hadashboard.di.components.DaggerStorageComponent;
import com.bsunk.hadashboard.di.components.StorageComponent;
import com.bsunk.hadashboard.di.modules.StorageModule;

import timber.log.Timber;

/**
 * Created by Bharat on 3/4/2017.
 */

public class HADashboardApplication extends MultiDexApplication {

    private StorageComponent storageComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        storageComponent = DaggerStorageComponent.builder()
                .storageModule(new StorageModule(this))
                .build();

    }

    public StorageComponent getStorageComponent() {
        return storageComponent;
    }


}
