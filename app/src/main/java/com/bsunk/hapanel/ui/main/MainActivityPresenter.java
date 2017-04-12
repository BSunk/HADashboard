package com.bsunk.hapanel.ui.main;

import com.bsunk.hapanel.data.DataManager;

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

    private MainActivityContract.View mView;
    private DataManager dataManager;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public MainActivityPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void subscribe(MainActivityContract.View view) {
        mView = view;
        subscribeToWebSocketEvents();
        mView.startStopConnectionService(true);
        mView.setTitle(dataManager.getSharedPrefHelper().getLocationName());
    }

    public void unSubscribe() {
        mView.startStopConnectionService(false);
        disposables.dispose();
        mView = null;
    }

    //Subscribes to websocket events and calls the view to change the connection image and color
    private void subscribeToWebSocketEvents() {
        disposables.add(dataManager.getWebSocketConnection().getWebSocketEventsBus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer event) {
                        mView.setConnectionImage(event);
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onComplete() {}
                }));
    }

}
