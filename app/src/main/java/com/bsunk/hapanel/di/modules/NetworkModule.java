package com.bsunk.hapanel.di.modules;

import com.bsunk.hapanel.data.local.DatabaseHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.services.ConnectionService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Bryan on 3/4/2017.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    OkHttpClient providesOKHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    ConnectionService providesConnectionService() { return new ConnectionService();}

    @Singleton
    @Provides
    WebSocketConnection providesWebSocketConnection(OkHttpClient client, DatabaseHelper databaseHelper, ConnectionService service) {
        return new WebSocketConnection(client, databaseHelper, service);
    }

}
