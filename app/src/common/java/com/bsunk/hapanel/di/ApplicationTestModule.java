package com.bsunk.hapanel.di;

import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;

import com.bsunk.hapanel.data.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by bryan on 4/27/17.
 */

@Module
public class ApplicationTestModule {

    protected final Application mApplication;

    public ApplicationTestModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    /************* MOCKS *************/

    @Provides
    @Singleton
    DataManager providesDataManager() {
        return mock(DataManager.class);
    }

}
