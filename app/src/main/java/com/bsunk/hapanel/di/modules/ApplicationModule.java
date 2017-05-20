package com.bsunk.hapanel.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.DeviceRepository;
import com.bsunk.hapanel.data.local.ModelDatabase;
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
    WebSocketConnection providesWebSocketConnection(OkHttpClient client, SharedPrefHelper sharedPrefHelper) {
        return new WebSocketConnection(client, sharedPrefHelper);
    }

    @Singleton
    @Provides
    DataManager providesDataManager(SharedPrefHelper sharedPrefHelper,
                                    WebSocketConnection webSocketConnection) {
        return new DataManager(sharedPrefHelper, webSocketConnection);
    }

    @Singleton
    @Provides
    SharedPrefHelper providesSharedPrefHelper() {
        return new SharedPrefHelper(mApplication);
    }

    @Provides
    @Singleton
    ModelDatabase providesModelDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ModelDatabase.class, "ha_db").build();
    }

    @Provides
    @Singleton
    DeviceRepository providesDeviceRepository(ModelDatabase modelDatabase) {
        return new DeviceRepository(modelDatabase);
    }

}