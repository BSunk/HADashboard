package com.bsunk.hapanel.ui.groups;

import com.bsunk.hapanel.data.remote.WebSocketConnection;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bryan on 6/3/17.
 */

public class GroupsPresenter implements GroupsContract.Presenter{

    private WebSocketConnection webSocketConnection;
    private GroupsContract.View mView;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public GroupsPresenter(WebSocketConnection webSocketConnection) {
        this.webSocketConnection = webSocketConnection;
    }

    @Override
    public void subscribe(GroupsContract.View view) {
        mView = view;
        subscribeToDeviceListUpdates();
    }

    private void subscribeToDeviceListUpdates() {
        disposable.add(webSocketConnection.getDeviceModelsBus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<WebSocketConnection.DeviceListUpdateModel>() {
                    @Override
                    public void onNext(WebSocketConnection.DeviceListUpdateModel deviceListUpdateModel) {
                        if(deviceListUpdateModel!=null) {
                            if (deviceListUpdateModel.updateID == -1) {

                            } else {

                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onComplete() {}
                }));
    }
}
