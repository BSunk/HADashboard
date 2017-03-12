package com.bsunk.hapanel.data.remote;

import com.bsunk.hapanel.data.model.DeviceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
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

    private WebSocket ws;

    @Inject
    public WebSocketConnection(OkHttpClient client)
    {
        mClient = client;
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
                        parseStateMessage(text);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseStateMessage(String data) {

        Observable<ArrayList<DeviceModel>> parseStatesObservable = Observable.create(e -> {
            try {
                ArrayList<DeviceModel> devices = new ArrayList<>();
                JSONObject statesResult = new JSONObject(data);
                JSONArray result = statesResult.getJSONArray("result");
                for(int i=0; i<result.length(); i++) {
                    JSONObject device = result.getJSONObject(i);
                    devices.add(new DeviceModel(device.getString("entity_id"),
                            device.getString("state"),
                            device.getString("last_updated"),
                            device.getString("attributes")));
                }
                e.onNext(devices);
            } catch (JSONException j) {
                e.onError(j);
            }
        });

        parseStatesObservable.subscribeWith(new DisposableObserver<ArrayList<DeviceModel>>() {
            @Override
            public void onNext(ArrayList<DeviceModel> deviceModels) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
