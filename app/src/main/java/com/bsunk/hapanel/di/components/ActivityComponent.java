package com.bsunk.hapanel.di.components;

import com.bsunk.hapanel.di.PerActivity;
import com.bsunk.hapanel.di.modules.ActivityModule;
import com.bsunk.hapanel.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by bryan on 4/11/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
