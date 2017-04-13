package com.bsunk.hapanel.ui.home;

import com.bsunk.hapanel.data.DataManager;

import javax.inject.Inject;

/**
 * Created by bryan on 4/10/17.
 */

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter {

    private DataManager dataManager;
    private HomeFragmentContract.View mView;

    @Inject
    public HomeFragmentPresenter(DataManager dataManager) {
        this.dataManager = dataManager;

    }

    @Override
    public void subscribe(HomeFragmentContract.View view) {
        this.mView = view;
    }

    @Override
    public void unSubscribe() {
        mView = null;
    }

}
