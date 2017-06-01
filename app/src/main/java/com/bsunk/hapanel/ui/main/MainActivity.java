package com.bsunk.hapanel.ui.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.databinding.ActivityMainBinding;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;
import com.bsunk.hapanel.services.ConnectionService;
import com.bsunk.hapanel.data.Constants;
import com.bsunk.hapanel.ui.home.HomeFragment;
import com.bsunk.hapanel.ui.settings.SettingsFragment;

import javax.inject.Inject;

import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CONNECTED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_FAILED;

//Main activity that contains the bottom navigation bar and the top
// status bar indicating connection status.

public class MainActivity extends AppCompatActivity implements MainActivityContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private ActivityComponent mActivityComponent;
    private ImageView editIV;

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        editIV = binding.editIv;
        presenter.subscribe(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment(), "").commit();

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        //hide navbar and status bar on config change
        onWindowFocusChanged(true);

    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(HAApplication.get(this).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    //Starts or stops the WebSocket service
    public void startStopConnectionService(boolean shouldStart) {
        Intent intent = new Intent(this, ConnectionService.class);
        if(shouldStart) intent.setAction(Constants.ACTION.START_FOREGROUND_ACTION);
        else intent.setAction(Constants.ACTION.STOP_FOREGROUND_ACTION);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    public void setTitle(String name) {
        if (name!=null) {
            binding.haLocationName.setText(name);
        }
        else {
            binding.haLocationName.setText(getString(R.string.app_name));
        }
    }

    public void setConnectionImage(int event) {
        switch (event) {
            case EVENT_CONNECTED:
                ((Animatable) binding.connectionIv.getDrawable()).start();
                break;
            case EVENT_FAILED:
                ((Animatable) binding.connectionIv.getDrawable()).stop();
                break;
        }
    }

    //Sets immersion mode
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            final View decorView = this.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        switch (item.getItemId()) {
            case R.id.action_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment(), "").commit();
                editIV.setVisibility(View.VISIBLE);
                editIV.startAnimation(fadeIn);
                break;
            case R.id.action_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment(), "").commit();
                editIV.setVisibility(View.GONE);
                editIV.startAnimation(fadeOut);
                break;
        }
        return true;
    }
}
