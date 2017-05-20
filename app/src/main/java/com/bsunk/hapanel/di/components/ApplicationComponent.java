package com.bsunk.hapanel.di.components;

import android.app.Application;
import android.content.Context;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.di.ApplicationContext;
import com.bsunk.hapanel.di.modules.ApplicationModule;
import com.bsunk.hapanel.services.ConnectionService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by Bharat on 3/6/2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(ConnectionService service);
    void inject(HAApplication application);

    @ApplicationContext Context context();
    Application application();
    WebSocketConnection webSocketConnection();
    DataManager dataManager();

}
