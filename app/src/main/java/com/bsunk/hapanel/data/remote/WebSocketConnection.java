package com.bsunk.hapanel.data.remote;

import android.content.ContentValues;

import com.bsunk.hapanel.data.local.DatabaseContract;
import com.bsunk.hapanel.data.local.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import timber.log.Timber;

/**
 * Created by Bryan on 3/4/2017.
 */

public class WebSocketConnection extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private static final String TYPE_AUTH_OK = "auth_ok";
    private static final String TYPE_AUTH_REQUIRED = "auth_required";
    private static final String TYPE_AUTH_INVALID = "auth_invalid";
    private static final String TYPE_RESULT = "result";
    private static final String TYPE_EVENT = "event";

    private final OkHttpClient mClient;
    private char[] pw;
    private int id_counter=1;
    private boolean stateResult = false;

    private WebSocket ws;
    private DatabaseHelper dataBaseHelper;

    @Inject
    public WebSocketConnection(OkHttpClient client, DatabaseHelper dataBaseHelper)
    {
        mClient = client;
        this.dataBaseHelper = dataBaseHelper;
    }

    public void connect(String ip, String port, char[] pw) {

        Request request = new Request.Builder()
                .url("ws://"+ip+":"+port+"/api/websocket")
                .build();

        ws = mClient.newWebSocket(request, this);
        this.pw = pw;
    }

    public void close() {
        if(ws!=null) {
            ws.close(NORMAL_CLOSURE_STATUS, null);
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {}

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Timber.v("Receiving: " + text);
        try {
            JSONObject message = new JSONObject(text);
            String type = message.getString("type");
            if(type!=null) {
                switch(type) {
                    case TYPE_AUTH_OK:
                        Timber.v("Successfully connected!");
                        sendRequestDeviceStates();
                        sendSubscribeToEvents();
                        break;
                    case TYPE_AUTH_REQUIRED:
                        Timber.v("Password Required");
                        sendSecret();
                        break;
                    case TYPE_AUTH_INVALID:
                        Timber.v("Auth failed");
                        break;
                    case TYPE_RESULT:
                        if(stateResult) {
                            parseStateData(text);
                            stateResult=false;
                        }
                        break;
                    case TYPE_EVENT:
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        Timber.v("Closed: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

    private void sendSecret() {
        JSONObject authObject = new JSONObject();
        try {
            Timber.v("Sending authentication");
            authObject.put("type", "auth");
            authObject.put("api_password", new String(pw));
            ws.send(authObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendSubscribeToEvents() {
        JSONObject subscribeObject = new JSONObject();
        try {
            subscribeObject.put("id", id_counter);
            subscribeObject.put("type", "subscribe_events");
            subscribeObject.put("event_type", "state_changed");
            ws.send(subscribeObject.toString());
            id_counter++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestDeviceStates() {
        JSONObject subscribeObject = new JSONObject();
        try {
            subscribeObject.put("id", id_counter);
            subscribeObject.put("type", "get_states");
            ws.send(subscribeObject.toString());
            id_counter++;
            stateResult=true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseStateData(String data) {
        parseStateMessageObservable(data)
                .flatMapIterable(contentValues -> contentValues)
                .flatMap(contentValue -> dataBaseHelper.addOrUpdateDevice(contentValue))
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {dataBaseHelper.open();}
                    @Override
                    public void onNext(Long aLong) {}
                    @Override
                    public void onError(Throwable e) {Timber.v(e);}
                    @Override
                    public void onComplete() {
                        dataBaseHelper.close();
                    }
                });
    }

    private Observable<ArrayList<ContentValues>> parseStateMessageObservable(String data) {
        return Observable.create(e -> {
            try {
                ArrayList<ContentValues> devices = new ArrayList<>();
                JSONObject statesResult = new JSONObject(data);
                JSONArray result = statesResult.getJSONArray("result");
                for(int i=0; i<result.length(); i++) {
                    JSONObject device = result.getJSONObject(i);
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.HAPanel.COLUMN_ENTITY_ID, device.getString("entity_id"));
                    values.put(DatabaseContract.HAPanel.COLUMN_STATE, device.getString("state"));
                    values.put(DatabaseContract.HAPanel.COLUMN_ATTRIBUTES, device.getString("attributes"));
                    values.put(DatabaseContract.HAPanel.COLUMN_LAST_CHANGED, device.getString("last_updated"));
                    devices.add(values);
                }
                e.onNext(devices);
            } catch (JSONException j) {
                e.onError(j);
            }
        });
    }
}
