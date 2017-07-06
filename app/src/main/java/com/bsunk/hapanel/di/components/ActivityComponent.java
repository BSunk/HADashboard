package com.bsunk.hapanel.di.components;

import com.bsunk.hapanel.di.PerActivity;
import com.bsunk.hapanel.di.modules.ActivityModule;
import com.bsunk.hapanel.ui.LauncherActivity;
import com.bsunk.hapanel.ui.groups.GroupsFragment;
import com.bsunk.hapanel.ui.home.HomeFragment;
import com.bsunk.hapanel.ui.main.MainActivity;
import com.bsunk.hapanel.ui.settings.SettingsFragment;

import dagger.Component;

/**
 * Created by bryan on 4/11/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(HomeFragment homeFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(GroupsFragment groupsFragment);
    void inject(LauncherActivity launcherActivity);

}
