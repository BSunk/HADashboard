package com.bsunk.hapanel.di.modules;

import android.app.Application;
import android.content.Context;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.DatabaseHelper;
import com.bsunk.hapanel.data.local.DbOpenHelper;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Bryan on 3/5/2017.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    OkHttpClient providesOKHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    WebSocketConnection providesWebSocketConnection(OkHttpClient client, DatabaseHelper databaseHelper, SharedPrefHelper sharedPrefHelper) {
        return new WebSocketConnection(client, databaseHelper, sharedPrefHelper);
    }

    @Singleton
    @Provides
    DataManager providesDataManager(SharedPrefHelper sharedPrefHelper,
                                    DatabaseHelper databaseHelper,
                                    WebSocketConnection webSocketConnection) {
        return new DataManager(sharedPrefHelper, databaseHelper, webSocketConnection);
    }

    @Singleton
    @Provides
    SharedPrefHelper providesSharedPrefHelper() {
        return new SharedPrefHelper(mApplication);
    }

    @Singleton
    @Provides
    DatabaseHelper providesDataBaseHelper(DbOpenHelper dbOpenHelper) {
        return new DatabaseHelper(dbOpenHelper);
    }

    @Singleton
    @Provides
    DbOpenHelper providesDbOpenHelper() {
        return new DbOpenHelper(mApplication);
    }

}