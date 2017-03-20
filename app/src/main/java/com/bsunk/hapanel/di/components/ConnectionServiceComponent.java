package com.bsunk.hapanel.di.components;


import com.bsunk.hapanel.di.modules.NetworkModule;
import com.bsunk.hapanel.di.modules.StorageModule;
import com.bsunk.hapanel.services.ConnectionService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bharat on 3/6/2017.
 */
@Singleton
@Component(dependencies = ApplicationComponent.class, modules = {StorageModule.class, NetworkModule.class})
public interface ConnectionServiceComponent {
    void inject(ConnectionService connectionService);
}
