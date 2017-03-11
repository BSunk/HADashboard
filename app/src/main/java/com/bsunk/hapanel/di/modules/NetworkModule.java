package com.bsunk.hapanel.di.modules;

import com.bsunk.hapanel.data.remote.WebSocketConnection;

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
    WebSocketConnection providesWebSocketConnection(OkHttpClient client) {
        return new WebSocketConnection(client);
    }

}
