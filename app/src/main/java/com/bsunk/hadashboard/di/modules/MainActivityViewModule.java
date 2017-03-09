package com.bsunk.hadashboard.di.modules;

import com.bsunk.hadashboard.ui.main.MainActivityContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bryan on 3/8/2017.
 */
@Module
public class MainActivityViewModule {

    private MainActivityContract.View mView;

    MainActivityViewModule(MainActivityContract.View view) {
        mView = view;
    }

    @Provides
    MainActivityContract.View providesMainActivityView() {
        return mView;
    }

}
