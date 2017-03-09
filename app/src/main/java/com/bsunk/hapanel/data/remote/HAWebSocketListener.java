package com.bsunk.hapanel.data.remote;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import timber.log.Timber;

/**
 * Created by Bryan on 3/5/2017.
 */

public class HAWebSocketListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
//        webSocket.send("Knock, knock!");
//        webSocket.send("Hello!");
//        webSocket.send(ByteString.decodeHex("deadbeef"));
//        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Timber.v("Receiving: " + text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Timber.v("Receiving: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        Timber.v("Closing: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

}
