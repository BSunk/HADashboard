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
    public MainActivityPresenter(MainActivityContract.View view, DataManager dataManager) {
        mView = view;
        this.dataManager = dataManager;
        connectToServer();
    }

    @Override
    public void connectToServer() {
        //String pw = dataManager.getSharedPrefHelper().getPW();
        String pw = "barru586";
        char[] charArray = pw.toCharArray();
      //  TODO: Add password hashing and storage
        dataManager.getWebSocketConnection().connect("192.168.10.113", "8123", charArray);
        //dataManager.getWebSocketConnection().close();
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
