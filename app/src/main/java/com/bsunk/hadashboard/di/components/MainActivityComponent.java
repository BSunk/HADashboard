package com.bsunk.hadashboard.di.components;

import com.bsunk.hadashboard.di.modules.MainActivityViewModule;
import com.bsunk.hadashboard.di.modules.NetworkModule;
import com.bsunk.hadashboard.di.modules.StorageModule;
import com.bsunk.hadashboard.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bharat on 3/6/2017.
 */
@Singleton
@Component(dependencies = ApplicationComponent.class, modules = {StorageModule.class, NetworkModule.class, MainActivityViewModule.class})
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
