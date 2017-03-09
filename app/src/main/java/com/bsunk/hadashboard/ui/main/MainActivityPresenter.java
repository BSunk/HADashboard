package com.bsunk.hadashboard.ui.main;

import com.bsunk.hadashboard.data.local.SharedPrefHelper;
import com.bsunk.hadashboard.data.remote.WebSocketConnection;

import javax.inject.Inject;

/**
 * Created by Bharat on 3/8/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final MainActivityContract.View mView;

    @Inject
    MainActivityPresenter(MainActivityContract.View view, SharedPrefHelper sharedPrefHelper, WebSocketConnection connection) {
        mView = view;
    }

    @Override
    public void connectToServer() {

    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
