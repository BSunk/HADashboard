package com.bsunk.hapanel.ui.home;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.data.remote.WebSocketConnection;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by bryan on 4/10/17.
 */

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter {

    private DataManager dataManager;
    private HomeFragmentContract.View mView;
    private WebSocketConnection webSocketConnection;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public HomeFragmentPresenter(DataManager dataManager, WebSocketConnection webSocketConnection) {
        this.dataManager = dataManager;
        this.webSocketConnection = webSocketConnection;
    }

    @Override
    public void subscribe(HomeFragmentContract.View view) {
        this.mView = view;
        subscribeToDeviceListUpdates();
    }

    @Override
    public void unSubscribe() {
        mView = null;
        disposable.dispose();
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
                                mView.showHideLoading(true);
                                mView.initializeRecyclerView(deviceListUpdateModel.list);
                            } else {
                                mView.updateList(deviceListUpdateModel);
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
