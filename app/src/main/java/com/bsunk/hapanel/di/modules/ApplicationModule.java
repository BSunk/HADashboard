package com.bsunk.hapanel.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bryan on 3/5/2017.
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context providesContext() {
        return mContext;
    }

}
