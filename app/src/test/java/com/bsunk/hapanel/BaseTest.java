package com.bsunk.hapanel;


import org.junit.After;
import org.junit.BeforeClass;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bryan on 4/27/17.
 */

public class BaseTest {

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.reset();
    }
}
