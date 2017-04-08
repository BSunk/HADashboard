package com.bsunk.hapanel.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.DaggerConnectionServiceComponent;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.bsunk.hapanel.data.remote.WebSocketConnection.EVENT_AUTH_FAILED;
import static com.bsunk.hapanel.data.remote.WebSocketConnection.EVENT_CLOSED;
import static com.bsunk.hapanel.data.remote.WebSocketConnection.EVENT_CONNECTED;
import static com.bsunk.hapanel.data.remote.WebSocketConnection.EVENT_CONNECTING;
import static com.bsunk.hapanel.data.remote.WebSocketConnection.EVENT_FAILED;

public class ConnectionService extends Service {

    @Inject
    DataManager dataManager;

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mNotificationBuilder;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public ConnectionService() {
    }

    @Override
    public void onCreate() {
        Timber.v("Service onCreate");

        DaggerConnectionServiceComponent.builder()
                .applicationComponent(((HAApplication)getApplication()).getApplicationComponent())
                .build()
                .inject(this);

        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationBuilder = new NotificationCompat.Builder(this);

        //Gets events from publishsubject from WebSocketConnection class
        disposables.add(dataManager.getWebSocketConnection().webSocketEventsBus.subscribeWith(new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer event) {
                setNotificationText(event);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.v("Service onStartCommand");

        if(intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Notification n =
                    mNotificationBuilder
                            .setSmallIcon(R.drawable.ic_home_black_24dp)
                            .setContentTitle("HomeAssistant")
                            .build();

            mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, n);

            Completable.create(e -> {
                String pw = "barru586";
                char[] charArray = pw.toCharArray();
                dataManager.getWebSocketConnection().connect("192.168.10.113", "8123", charArray);
                e.onComplete();
            }).subscribeOn(Schedulers.newThread())
                    .subscribe();

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, n);
        }

        else if(intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i("Service", "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }

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
        disposables.clear();
    }

    private void setNotificationText(int eventCode) {
        Notification n = mNotificationBuilder.build();
        switch (eventCode) {
            case EVENT_CONNECTING:
                 n = mNotificationBuilder
                                .setContentText("Connecting...")
                                .build();
                break;
            case EVENT_CONNECTED:
                n = mNotificationBuilder
                        .setContentText("Connected")
                        .build();
                break;
            case EVENT_AUTH_FAILED:
                n = mNotificationBuilder
                        .setContentText("Authorization Failed")
                        .build();
                break;
            case EVENT_FAILED:
                n = mNotificationBuilder
                        .setContentText("Failed to connect")
                        .build();
                break;
            case EVENT_CLOSED:
                n = mNotificationBuilder
                        .setContentText("Connection closed")
                        .build();
                break;
        }

        mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, n);
    }
}
