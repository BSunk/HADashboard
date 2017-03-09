package com.bsunk.hapanel.data.remote;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * Created by Bryan on 3/4/2017.
 */

public class WebSocketConnection {

    @Inject OkHttpClient mClient;
    @Inject HAWebSocketListener mListener;

    private WebSocket ws;

    @Inject
    public WebSocketConnection(OkHttpClient client, HAWebSocketListener listener)
    {
        mClient = client;
        mListener = listener;
    }

    public void connect(String ip, String port) {

        Request request = new Request.Builder()
                .url("ws://"+ip+":"+port+"/api/websocket")
                .build();

        ws = mClient.newWebSocket(request, mListener);
    }

    public void close() {
        if(ws!=null) {
            ws.close(1000, null);
        }
    }

}
