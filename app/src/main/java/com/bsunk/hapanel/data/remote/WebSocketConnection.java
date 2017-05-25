package com.bsunk.hapanel.data.remote;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.DeviceRepository;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.local.entity.DeviceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import timber.log.Timber;

import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.LIGHT_TYPE;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_AUTH_FAILED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CLOSED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CONNECTED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CONNECTING;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_FAILED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.NORMAL_CLOSURE_STATUS;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.TYPE_AUTH_INVALID;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.TYPE_AUTH_OK;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.TYPE_AUTH_REQUIRED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.TYPE_EVENT;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.TYPE_RESULT;

/**
 * Created by Bryan on 3/4/2017.
 */

public class WebSocketConnection extends WebSocketListener {

    private final SharedPrefHelper sharedPrefHelper;
    private final DeviceRepository deviceRepository;
    private PublishSubject<Integer> webSocketEventsBus = PublishSubject.create();

    private final OkHttpClient mClient;

    private char[] pw;
    private int id_counter=1;
    private int configID;
    private int stateID;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private WebSocket ws;

    @Inject
    public WebSocketConnection(OkHttpClient client, DataManager dataManager)
    {
        mClient = client;
        sharedPrefHelper = dataManager.getSharedPrefHelper();
        deviceRepository = dataManager.getDeviceRepository();
    }

    public void connect(String ip, String port, char[] pw) {
        close();

        Request request = new Request.Builder()
                .url("ws://"+ip+":"+port+"/api/websocket")
                .build();

        ws = mClient.newWebSocket(request, this);

        webSocketEventsBus.onNext(EVENT_CONNECTING);

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
                        webSocketEventsBus.onNext(EVENT_CONNECTED);
                        Timber.v("Successfully connected!");
                        sendRequestDeviceStates();
                        sendRequestConfig();
                        sendSubscribeToEvents();
                        break;
                    case TYPE_AUTH_REQUIRED:
                        Timber.v("Password Required");
                        sendSecret();
                        break;
                    case TYPE_AUTH_INVALID:
                        Timber.v("Auth failed");
                        webSocketEventsBus.onNext(EVENT_AUTH_FAILED);
                        break;
                    case TYPE_RESULT:
                        parseResult(text);
                        break;
                    case TYPE_EVENT:
                        saveEventData(text);
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
        webSocketEventsBus.onNext(EVENT_CLOSED);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
        webSocketEventsBus.onNext(EVENT_FAILED);
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
            stateID = id_counter;
            id_counter++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestConfig() {
        JSONObject subscribeObject = new JSONObject();
        try {
            subscribeObject.put("id", id_counter);
            subscribeObject.put("type", "get_config");
            ws.send(subscribeObject.toString());
            configID = id_counter;
            id_counter++;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseResult(String data) {
        try {
            JSONObject result = new JSONObject(data);
            int id = result.getInt("id");
            if(id == configID) {
                parseConfigData(data);
            }
            else if(id == stateID) {
                saveStateData(data);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseConfigData(String data) {
        disposables.add(Single.create((SingleOnSubscribe<Boolean>) e -> {
            try {
                JSONObject config = new JSONObject(data).getJSONObject("result");
                sharedPrefHelper.putLocationName(config.getString("location_name"));
                sharedPrefHelper.putLat(config.getString("latitude"));
                sharedPrefHelper.putLong(config.getString("longitude"));
                sharedPrefHelper.putTimeZone(config.getString("time_zone"));
                sharedPrefHelper.putVersion(config.getString("version"));
                e.onSuccess(true);
            } catch (JSONException d) {
                d.printStackTrace();
                e.onError(d);
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean b) {
                        Timber.v("Successfully updated config data");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.v("Error updating config data");
                    }
                }));
    }

    private void saveStateData(String data) {
        disposables.add(parseStateDataObservable(data)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Void>() {
                    @Override
                    public void onNext(Void aVoid) {}

                    @Override
                    public void onError(Throwable e) {
                        Timber.v("Error inserting devices");
                        Timber.v(e);
                    }

                    @Override
                    public void onComplete() {
                        Timber.v("Inserted/Updated Devices");
                    }
                }));
    }

    private Observable<Void> parseStateDataObservable(String data) {
        return Observable.create(e -> {
            try {
                JSONObject statesResult = new JSONObject(data);
                JSONArray result = statesResult.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject device = result.getJSONObject(i);
                    String[] parts = device.getString("entity_id").split("\\.");
                    DeviceModel deviceModel = new DeviceModel(device.getString("entity_id"),
                            device.getString("state"),
                            device.getString("last_changed"),
                            device.getString("attributes"),
                            parts[0]);

                    long id = deviceRepository.addDevice(deviceModel);
                    if (id != -1) {
                        Timber.v("Inserted device with row ID: " + id + " and entityID: " + deviceModel.getEntity_id());
                    } else {
                        int updateID = deviceRepository.updateDevice(deviceModel);
                        Timber.v("Updated " + updateID + " device with entityID " + deviceModel.getEntity_id());
                    }
                }
                e.onComplete();
            }
            catch (JSONException j) {
                e.onError(j);
            }
        });
    }

    private void saveEventData(String data) {
        disposables.add(parseEventDataObservable(data)
                .map(deviceRepository::updateDevice)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer aInt) {
                        Timber.v("Updated " + aInt + " device");
                    }
                    @Override
                    public void onError(Throwable e) {Timber.v(e);}
                    @Override
                    public void onComplete() {
                    }
                }));
    }

    private Observable<DeviceModel> parseEventDataObservable(String data) {
        return Observable.create( e -> {
            try {
                JSONObject device = new JSONObject(data).getJSONObject("event").getJSONObject("data").getJSONObject("new_state");
                DeviceModel model = new DeviceModel();
                model.setEntity_id(device.getString("entity_id"));
                model.setState(device.getString("state"));
                model.setLast_changed(device.getString("last_changed"));
                model.setAttributes(device.getString("attributes"));
                e.onNext(model);

            } catch (JSONException d) {
                e.onError(d);
            }
        });
    }

    public void onDestroy() {
        disposables.dispose();
    }

    public PublishSubject<Integer> getWebSocketEventsBus() {
        return webSocketEventsBus;
    }

}
