package com.bsunk.hapanel.data;

import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;

import javax.inject.Inject;

/**
 * Created by Bharat on 3/8/2017.
 */

public class DataManager {

    private final SharedPrefHelper sharedPrefHelper;
    private final WebSocketConnection webSocketConnection;

    @Inject
    public DataManager(SharedPrefHelper sharedPrefHelper, WebSocketConnection webSocketConnection) {
        this.sharedPrefHelper = sharedPrefHelper;
        this.webSocketConnection = webSocketConnection;
    }

    public SharedPrefHelper getSharedPrefHelper() {
        return sharedPrefHelper;
    }

    public WebSocketConnection getWebSocketConnection() {
        return webSocketConnection;
    }

}
