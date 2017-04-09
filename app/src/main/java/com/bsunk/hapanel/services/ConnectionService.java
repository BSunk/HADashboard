package com.bsunk.hapanel.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.Constants;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.di.components.DaggerConnectionServiceComponent;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

import static com.bsunk.hapanel.data.Constants.ACTION.RETRY_CONNECTION_ACTION;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_AUTH_FAILED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CLOSED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CONNECTED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CONNECTING;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_FAILED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_NO_SERVER;

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

        //Gets events from PublishSubject from WebSocketConnection class
        disposables.add(dataManager.getWebSocketConnection().webSocketEventsBus
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer event) {
                        setNotificationText(event);
                    }
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onComplete() {}
                }));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction().equals(Constants.ACTION.START_FOREGROUND_ACTION)) {

            Notification n =
                    mNotificationBuilder
                            .setSmallIcon(R.drawable.ic_home_black_24dp)
                            .setContentText(getString(R.string.ws_connecting))
                            .setContentTitle(getString(R.string.app_name))
                            .build();

            mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, n);

            dataManager.getWebSocketConnection().close();
            connectToServer("192.168.10.113", "8123", "barru586");
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, n);
        }

        else if(intent.getAction().equals(Constants.ACTION.STOP_FOREGROUND_ACTION)) {
            Timber.v("Received Stop Foreground Intent");
            stopForeground(true);
            dataManager.getWebSocketConnection().close();
            stopSelf(startId);
        }
        else if(intent.getAction().equals(RETRY_CONNECTION_ACTION)) {
            connectToServer("192.168.10.113", "8123", "barru586");
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
        disposables.clear();
    }

    private void setNotificationText(int eventCode) {
        mNotificationBuilder.mActions.clear();
        Notification n = mNotificationBuilder.build();

        Intent intent = new Intent(this, ConnectionService.class);
        intent.setAction(RETRY_CONNECTION_ACTION);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        switch (eventCode) {
            case EVENT_CONNECTING:
                 n = mNotificationBuilder
                         .setContentText(getString(R.string.ws_connecting))
                         .build();
                break;
            case EVENT_CONNECTED:
                n = mNotificationBuilder
                        .setContentText(getString(R.string.ws_connected))
                        .build();
                break;
            case EVENT_AUTH_FAILED:
                n = mNotificationBuilder
                        .setContentText(getString(R.string.ws_auth_failed))
                        .build();
                break;
            case EVENT_FAILED:
                n = mNotificationBuilder
                        .setContentText(getString(R.string.ws_failed))
                        .addAction(R.drawable.ic_refresh_black_24dp, getString(R.string.retry_notification_button), pendingIntent)
                        .build();
                break;
            case EVENT_CLOSED:
                n = mNotificationBuilder
                        .setContentText(getString(R.string.ws_closed))
                        .addAction(R.drawable.ic_refresh_black_24dp, getString(R.string.reconnect_notification_button), pendingIntent)
                        .build();
                break;
            case EVENT_NO_SERVER:
                n = mNotificationBuilder
                        .setContentText(getString(R.string.ws_no_info))
                        .build();
                break;
        }

        mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, n);
    }

    private void connectToServer(String server, String port, String pw) {
        Completable.create(e -> {
            char[] charArray = pw.toCharArray();
            dataManager.getWebSocketConnection().connect(server, port, charArray);
            e.onComplete();
        }).subscribe();
    }

}
