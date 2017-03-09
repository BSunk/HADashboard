package com.bsunk.hapanel.ui.main;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;

import javax.inject.Inject;

/**
 * Created by Bharat on 3/8/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final MainActivityContract.View mView;
    private final DataManager dataManager;

    @Inject
    MainActivityPresenter(MainActivityContract.View view, DataManager dataManager) {
        mView = view;
        this.dataManager = dataManager;
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
