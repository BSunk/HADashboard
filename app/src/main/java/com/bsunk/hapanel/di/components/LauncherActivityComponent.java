package com.bsunk.hapanel.di.components;

import com.bsunk.hapanel.di.modules.NetworkModule;
import com.bsunk.hapanel.di.modules.StorageModule;
import com.bsunk.hapanel.ui.LauncherActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bharat on 3/6/2017.
 */
@Singleton
@Component(dependencies = ApplicationComponent.class, modules = {NetworkModule.class, StorageModule.class})
public interface LauncherActivityComponent {
    void inject(LauncherActivity activity);
}
