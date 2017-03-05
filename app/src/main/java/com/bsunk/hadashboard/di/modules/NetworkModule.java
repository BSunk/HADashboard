package com.bsunk.hadashboard.di.modules;

import com.bsunk.hadashboard.data.remote.HAWebSocketListener;
import com.bsunk.hadashboard.data.remote.WebSocketConnection;

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
    public OkHttpClient providesOKHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public HAWebSocketListener provideHAWebSocketListener() {
        return new HAWebSocketListener();
    }

    @Singleton
    @Provides
    public WebSocketConnection providesWebSocketConnection(OkHttpClient client, HAWebSocketListener listener) {
        return new WebSocketConnection(client, listener);
    }

}
