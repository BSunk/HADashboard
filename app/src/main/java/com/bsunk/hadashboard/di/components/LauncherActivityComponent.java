package com.bsunk.hadashboard.di.components;

import com.bsunk.hadashboard.di.modules.StorageModule;
import com.bsunk.hadashboard.ui.LauncherActivity;

import dagger.Component;

/**
 * Created by Bharat on 3/6/2017.
 */
@Component(dependencies = ApplicationComponent.class, modules = StorageModule.class)
public interface LauncherActivityComponent {
    void inject(LauncherActivity activity);
}
