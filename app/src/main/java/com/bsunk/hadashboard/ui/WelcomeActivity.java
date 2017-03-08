package com.bsunk.hadashboard.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.hadashboard.R;
import com.redbooth.WelcomeCoordinatorLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.welcome_coordinator)
    WelcomeCoordinatorLayout welcomeCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        welcomeCoordinatorLayout.addPage(R.layout.welcome_page_1, R.layout.welcome_page_2);
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }
}
