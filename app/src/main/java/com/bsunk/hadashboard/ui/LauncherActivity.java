package com.bsunk.hadashboard.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.hadashboard.HADashboardApplication;
import com.bsunk.hadashboard.R;
import com.bsunk.hadashboard.data.local.SharedPrefHelper;
import com.bsunk.hadashboard.di.components.DaggerLauncherActivityComponent;
import com.bsunk.hadashboard.di.modules.StorageModule;
import com.bsunk.hadashboard.ui.main.MainActivity;

import javax.inject.Inject;


public class LauncherActivity extends AppCompatActivity {

    @Inject
    SharedPrefHelper sharedPrefHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        DaggerLauncherActivityComponent
                .builder()
                .applicationComponent(((HADashboardApplication)getApplication()).getApplicationComponent())
                .storageModule(new StorageModule())
                .build()
                .inject(this);

        initialize();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        finish();
    }

    private void initialize() {
        Intent intent;
        if (sharedPrefHelper.getWelcomeScreenLaunched()) {
            intent = MainActivity.getStartIntent(getApplicationContext());
        } else {
            intent = WelcomeActivity.getStartIntent(getApplicationContext());
        }
        startActivity(intent);
    }
}
