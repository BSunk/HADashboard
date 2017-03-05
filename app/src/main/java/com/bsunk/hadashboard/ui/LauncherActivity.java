package com.bsunk.hadashboard.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.hadashboard.R;
import com.bsunk.hadashboard.data.remote.WebSocketConnection;
import com.bsunk.hadashboard.di.components.DaggerNetworkComponent;

import javax.inject.Inject;

public class LauncherActivity extends AppCompatActivity {

    @Inject
    WebSocketConnection webSocketConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        DaggerNetworkComponent.builder().build().inject(this);
        connect();
        close();
    }

    private void connect() {
        webSocketConnection.connect("192.168.10.101", "8123");
    }

    private void  close() {
        webSocketConnection.close();
    }
}
