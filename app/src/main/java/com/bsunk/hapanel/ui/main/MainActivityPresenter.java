package com.bsunk.hapanel.ui.main;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.remote.WebSocketConnection;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Bharat on 3/8/2017.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final WebSocketConnection webSocketConnection;
    private MainActivityContract.View mView;
    private DataManager dataManager;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public MainActivityPresenter(DataManager dataManager, WebSocketConnection webSocketConnection) {
        this.webSocketConnection = webSocketConnection;
        this.dataManager = dataManager;
    }

    public void subscribe(MainActivityContract.View view) {
        setView(view);
        disposables.add(subscribeToWebSocketEvents());
        mView.startStopConnectionService(true);
        mView.setTitle(dataManager.getSharedPrefHelper().getLocationName());
    }

    public void unSubscribe() {
        mView.startStopConnectionService(false);
        disposables.dispose();
        mView = null;
    }

    public void setView(MainActivityContract.View view) {
        mView = view;
    }

    //Subscribes to websocket events and calls the view to change the connection image and color
    private DisposableObserver<Integer> subscribeToWebSocketEvents() {
        return webSocketConnection.getWebSocketEventsBus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(webSocketEventsDisposableObserver());
    }

    public DisposableObserver<Integer> webSocketEventsDisposableObserver() {
        return new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                mView.setConnectionImage(integer);
            }
            @Override
            public void onError(Throwable e) {e.printStackTrace();}
            @Override
            public void onComplete() {}
        };
    }

}
