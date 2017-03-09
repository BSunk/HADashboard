package com.bsunk.hapanel.di.modules;

import android.content.Context;

import com.bsunk.hapanel.data.local.SharedPrefHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bryan on 3/5/2017.
 */
@Module
public class StorageModule {

    @Provides
    SharedPrefHelper providesSharedPrefHelper(Context context) {
        return new SharedPrefHelper(context);
    }

}
