package com.bsunk.hapanel.ui.main;

import com.bsunk.hapanel.data.DataManager;

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
        mView.stopConnectionService();
    }

    @Override
    public void connectToServer() {
        //mView.startConnectionService();
    }
}
