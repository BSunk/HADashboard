package com.bsunk.hadashboard.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.hadashboard.HADashboardApplication;
import com.bsunk.hadashboard.R;
import com.bsunk.hadashboard.data.local.SharedPrefHelper;
import com.bsunk.hadashboard.data.remote.WebSocketConnection;
import com.bsunk.hadashboard.di.components.DaggerMainActivityComponent;
import com.bsunk.hadashboard.di.modules.NetworkModule;
import com.bsunk.hadashboard.di.modules.StorageModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    @Inject
    SharedPrefHelper sharedPrefHelper;
    @Inject
    WebSocketConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerMainActivityComponent
                .builder()
                .applicationComponent(((HADashboardApplication)getApplication()).getApplicationComponent())
                .storageModule(new StorageModule())
                .networkModule(new NetworkModule())
                .build()
                .inject(this);

    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void setPresenter(MainActivityContract.Presenter presenter) {

    }
}
