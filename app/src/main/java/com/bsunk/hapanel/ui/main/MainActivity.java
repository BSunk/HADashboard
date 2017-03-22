package com.bsunk.hapanel.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.di.components.DaggerMainActivityComponent;
import com.bsunk.hapanel.di.modules.MainActivityPresenterModule;
import com.bsunk.hapanel.di.modules.NetworkModule;
import com.bsunk.hapanel.di.modules.StorageModule;
import com.bsunk.hapanel.services.ConnectionService;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainActivityComponent
                .builder()
                .applicationComponent(((HAApplication)getApplication()).getApplicationComponent())
                .mainActivityPresenterModule(new MainActivityPresenterModule(this))
                .build()
                .inject(this);

    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public void startConnectionService() {
        Intent intent = new Intent(this, ConnectionService.class);
        startService(intent);
    }

    public void stopConnectionService() {
        Intent intent = new Intent(this, ConnectionService.class);
        stopService(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

}
