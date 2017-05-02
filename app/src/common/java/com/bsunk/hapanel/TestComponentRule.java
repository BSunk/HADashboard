package com.bsunk.hapanel;

import android.content.Context;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.ApplicationTestModule;
import com.bsunk.hapanel.di.TestComponent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by bryan on 4/27/17.
 */

public class TestComponentRule implements TestRule {

    private TestComponent mTestComponent;
    private Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getMockDataManager() {
        return mTestComponent.dataManager();
    }

    private void setupDaggerTestComponentInApplication() {
        HAApplication application = HAApplication.get(mContext);
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(application))
                .build();
        application.setComponent(mTestComponent);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}