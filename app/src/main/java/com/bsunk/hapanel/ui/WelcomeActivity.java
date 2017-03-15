package com.bsunk.hapanel.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.hapanel.R;
import com.bsunk.hapanel.databinding.ActivityWelcomeBinding;
import com.redbooth.WelcomeCoordinatorLayout;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWelcomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        binding.welcomeCoordinator.addPage(R.layout.welcome_page_1, R.layout.welcome_page_2);
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, WelcomeActivity.class);
    }
}
