package com.bsunk.hapanel.di.modules;

import android.content.Context;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.DatabaseHelper;
import com.bsunk.hapanel.data.local.DbOpenHelper;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bryan on 3/5/2017.
 */
@Module
public class StorageModule {

    @Singleton
    @Provides
    DataManager providesDataManager(SharedPrefHelper sharedPrefHelper,
                                    DatabaseHelper databaseHelper,
                                    WebSocketConnection webSocketConnection) {
        return new DataManager(sharedPrefHelper, databaseHelper, webSocketConnection);
    }

    @Singleton
    @Provides
    SharedPrefHelper providesSharedPrefHelper(Context context) {
        return new SharedPrefHelper(context);
    }

    @Singleton
    @Provides
    DatabaseHelper providesDataBaseHelper(DbOpenHelper dbOpenHelper) {
        return new DatabaseHelper(dbOpenHelper);
    }

    @Singleton
    @Provides
    DbOpenHelper providesDbOpenHelper(Context context) {
        return new DbOpenHelper(context);
    }

}
