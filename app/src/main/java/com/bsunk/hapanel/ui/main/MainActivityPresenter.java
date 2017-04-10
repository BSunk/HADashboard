package com.bsunk.hapanel.ui.main;

import com.bsunk.hapanel.data.DataManager;

import javax.inject.Inject;

/**
 * Created by Bharat on 3/8/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final MainActivityContract.View mView;
    private DataManager dataManager;

    @Inject
    public MainActivityPresenter(MainActivityContract.View view, DataManager dataManager) {
        mView = view;
        this.dataManager = dataManager;
    }

    public void subscribe() {
        mView.startConnectionService();
        mView.setTitle(dataManager.getSharedPrefHelper().getLocationName());
    }

    public void unSubscribe() {
        mView.stopConnectionService();
    }

}
