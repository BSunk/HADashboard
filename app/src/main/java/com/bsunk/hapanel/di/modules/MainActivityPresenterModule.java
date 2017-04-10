package com.bsunk.hapanel.di.modules;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.ui.main.MainActivity;
import com.bsunk.hapanel.ui.main.MainActivityContract;
import com.bsunk.hapanel.ui.main.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bryan on 3/8/2017.
 */
@Module
public class MainActivityPresenterModule {

    private MainActivityContract.View mView;

    public MainActivityPresenterModule(MainActivityContract.View view) {
        mView = view;
    }

    @Singleton
    @Provides
    MainActivityContract.View providesMainActivityView() {
        return mView;
    }

    @Singleton
    @Provides
    MainActivityContract.Presenter providesMainActivityPresenter(MainActivityContract.View view, DataManager dataManager) {
        return new MainActivityPresenter(view, dataManager);
    }

}
