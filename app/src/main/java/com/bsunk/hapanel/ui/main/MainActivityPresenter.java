package com.bsunk.hapanel.ui.main;

import android.content.Intent;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.services.ConnectionService;

import javax.inject.Inject;

/**
 * Created by Bharat on 3/8/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final MainActivityContract.View mView;
    private final DataManager dataManager;

    @Inject
    public MainActivityPresenter(MainActivityContract.View view, DataManager dataManager) {
        mView = view;
        this.dataManager = dataManager;
        connectToServer();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void connectToServer() {
        mView.startConnectionService();
    }
}
