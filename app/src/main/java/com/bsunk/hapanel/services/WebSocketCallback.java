package com.bsunk.hapanel.services;

/**
 * Created by bryan on 4/5/17.
 */

public interface WebSocketCallback {
    void onConnectionFailure();
    void onConnectionClosed();
    void onConnectionSuccess();
}
