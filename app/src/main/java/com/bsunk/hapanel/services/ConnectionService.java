package com.bsunk.hapanel.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.DaggerConnectionServiceComponent;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ConnectionService extends Service {

    @Inject
    DataManager dataManager;

    public ConnectionService() {
    }

    @Override
    public void onCreate() {
        Timber.v("Service onCreate");

        DaggerConnectionServiceComponent.builder()
                .applicationComponent(((HAApplication)getApplication()).getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.v("Service onStartCommand");

        Completable.create(e -> {
            String pw = "barru586";
            char[] charArray = pw.toCharArray();
            dataManager.getWebSocketConnection().connect("192.168.10.113", "8123", charArray);
            e.onComplete();
        }).subscribeOn(Schedulers.newThread())
        .subscribe();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Timber.v("Service onDestroy");
        dataManager.getWebSocketConnection().close();
    }

}
