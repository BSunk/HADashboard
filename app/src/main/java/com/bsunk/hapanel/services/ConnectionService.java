package com.bsunk.hapanel.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.DaggerConnectionServiceComponent;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ConnectionService extends Service {

    private final int notificationID = 1;
    private NotificationManager mNotificationManager;
    private Notification n;

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

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_home_black_24dp)
                        .setContentTitle("Connected to HomeAssistant Server.");


        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        n = mBuilder.build();
        n.flags = Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(notificationID, n);

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
        mNotificationManager.cancel(notificationID);
    }

}
