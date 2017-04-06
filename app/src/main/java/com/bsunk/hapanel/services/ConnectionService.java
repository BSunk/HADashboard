package com.bsunk.hapanel.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.DaggerConnectionServiceComponent;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ConnectionService extends Service implements WebSocketCallback {

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

        if(intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_home_black_24dp)
                            .setContentTitle("Connected to HomeAssistant Server.");

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification n = mBuilder.build();
            n.flags = Notification.FLAG_NO_CLEAR;
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
    }

    @Override
    public void onConnectionFailure() {
        Timber.v("Connection failed in Service class");
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onConnectionClosed() {
        stopForeground(true);
        stopSelf();
    }

    @Override
    public void onConnectionSuccess() {

    }
}
