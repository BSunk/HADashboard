package com.bsunk.hadashboard.di.components;

import android.content.Context;

import com.bsunk.hadashboard.di.modules.ApplicationModule;

import dagger.Component;

/**
 * Created by Bharat on 3/6/2017.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
}
