package com.bsunk.hapanel;

import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.ui.main.MainActivityContract;
import com.bsunk.hapanel.ui.main.MainActivityPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Observable;

import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_CONNECTED;
import static com.bsunk.hapanel.data.Constants.WEB_SOCKET_EVENTS.EVENT_FAILED;

/**
 * Created by bryan on 4/27/17.
 */

public class MainActivityPresenterTest extends BaseTest {

    @Mock
    private MainActivityContract.View mView;
    @Mock
    private DataManager dataManager;
    @Mock
    private WebSocketConnection webSocketConnection;

    private MainActivityPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenter(dataManager, webSocketConnection);
        presenter.setView(mView);
    }

    @Test
    public void shouldPassEventConnectedOnSubscribeToWebSocket() {
        Observable.just(EVENT_CONNECTED).subscribeWith(presenter.webSocketEventsDisposableObserver());
        Mockito.verify(mView).setConnectionImage(EVENT_CONNECTED);
    }

    @Test
    public void shouldPassEventFailedOnSubscribeToWebSocket() {
        Observable.just(EVENT_FAILED).subscribeWith(presenter.webSocketEventsDisposableObserver());
        Mockito.verify(mView).setConnectionImage(EVENT_FAILED);
    }

}
