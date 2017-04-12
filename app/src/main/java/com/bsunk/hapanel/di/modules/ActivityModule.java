package com.bsunk.hapanel.di.modules;

import android.app.Activity;
import android.content.Context;

import com.bsunk.hapanel.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bryan on 4/11/17.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

}