package com.bsunk.hadashboard.di.components;

import com.bsunk.hadashboard.di.modules.NetworkModule;
import com.bsunk.hadashboard.ui.LauncherActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bharat on 3/4/2017.
 */
@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    void inject(LauncherActivity activity);

}
