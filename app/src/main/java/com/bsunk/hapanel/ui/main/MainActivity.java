package com.bsunk.hapanel.ui.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.databinding.ActivityMainBinding;
import com.bsunk.hapanel.di.components.DaggerMainActivityComponent;
import com.bsunk.hapanel.di.modules.MainActivityPresenterModule;
import com.bsunk.hapanel.services.ConnectionService;
import com.bsunk.hapanel.data.Constants;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    private ActivityMainBinding binding;

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        DaggerMainActivityComponent
                .builder()
                .applicationComponent(((HAApplication)getApplication()).getApplicationComponent())
                .mainActivityPresenterModule(new MainActivityPresenterModule(this))
                .build()
                .inject(this);

        presenter.subscribe();

    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public void startConnectionService() {
        Intent intent = new Intent(this, ConnectionService.class);
        intent.setAction(Constants.ACTION.START_FOREGROUND_ACTION);
        startService(intent);
    }

    public void stopConnectionService() {
        Intent intent = new Intent(this, ConnectionService.class);
        intent.setAction(Constants.ACTION.STOP_FOREGROUND_ACTION);
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

}
