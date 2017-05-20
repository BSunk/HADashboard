package com.bsunk.hapanel.ui.home;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.model.DeviceModel;

import java.util.ArrayList;

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

    private final CompositeDisposable disposable = new CompositeDisposable();

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
        disposable.dispose();
    }

    public void initDeviceList() {

//        disposable.add(dataManager.getDataBaseHelper().getAllDevices()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<ArrayList<DeviceModel>>() {
//            @Override
//            public void onNext(ArrayList<DeviceModel> deviceModels) {
//                mView.initializeRecyclerView(deviceModels);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }));
    }

}
