package com.bsunk.hadashboard.di.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bsunk.hadashboard.HADashboardApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bryan on 3/5/2017.
 */
@Module
public class StorageModule {

    HADashboardApplication application;

    public StorageModule(HADashboardApplication application) {this.application = application; }

    @Singleton
    @Provides
    SharedPreferences provideShardPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
