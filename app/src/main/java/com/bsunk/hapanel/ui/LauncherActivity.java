package com.bsunk.hapanel.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;
import com.bsunk.hapanel.ui.main.MainActivity;

import javax.inject.Inject;


public class LauncherActivity extends AppCompatActivity {

    @Inject
    DataManager dataManager;

    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        activityComponent().inject(this);

        initialize();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        finish();
    }
//TEST
    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(HAApplication.get(this).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    private void initialize() {
        Intent intent;
        if (dataManager.getSharedPrefHelper().getWelcomeScreenLaunched()) {
            intent = MainActivity.getStartIntent(getApplicationContext());
        } else {
            intent = WelcomeActivity.getStartIntent(getApplicationContext());
        }
        //intent = MainActivity.getStartIntent(getApplicationContext());
        startActivity(intent);
    }
}
