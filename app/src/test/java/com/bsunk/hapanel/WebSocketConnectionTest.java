package com.bsunk.hapanel;

import android.content.ContentValues;

import com.bsunk.hapanel.data.local.DatabaseContract;
import com.bsunk.hapanel.data.local.DatabaseHelper;
import com.bsunk.hapanel.data.local.SharedPrefHelper;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.testData.TestData;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import io.reactivex.observers.TestObserver;
import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

/**
 * Created by bryan on 4/27/17.
 */
@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class WebSocketConnectionTest extends BaseTest{

    @Mock
    private DatabaseHelper databaseHelper;
    @Mock
    private SharedPrefHelper sharedPrefHelper;
    @Mock
    private OkHttpClient client;

    private WebSocketConnection webSocketConnection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webSocketConnection = new WebSocketConnection(client, databaseHelper, sharedPrefHelper);
    }

    @Test
    public void shouldErrorWhenParseEventDataObservable() {
        TestObserver<ContentValues> testObserver = new TestObserver<>();
        webSocketConnection.parseEventDataObservable("").subscribe(testObserver);
        testObserver.assertError(JSONException.class);
    }

    @Test
    public void shouldPassWhenParseEventDataObservable() {
        TestObserver<ContentValues> testObserver = new TestObserver<>();
        webSocketConnection.parseEventDataObservable(TestData.testEventData).subscribe(testObserver);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.HAPanel.COLUMN_ENTITY_ID, "light.bed_light");
        values.put(DatabaseContract.HAPanel.COLUMN_STATE, "off");
        values.put(DatabaseContract.HAPanel.COLUMN_ATTRIBUTES, "{\"friendly_name\":\"Bed Light\",\"supported_features\":151}");
        values.put(DatabaseContract.HAPanel.COLUMN_LAST_CHANGED, "2017-04-29T02:10:57.875377+00:00");
        values.put(DatabaseContract.HAPanel.COLUMN_TYPE, "light");

        testObserver.onComplete();
        testObserver.assertResult(values);
        testObserver.assertNoErrors();
    }

    @Test
    public void shouldErrorWhenParseStateDataObservable() {
        TestObserver<ArrayList<ContentValues>> testObserver = new TestObserver<>();
        webSocketConnection.parseStateDataObservable("").subscribe(testObserver);
        testObserver.assertError(JSONException.class);
    }

    @Test
    public void shouldPassWhenParseStateDataObservable() {
        TestObserver<ArrayList<ContentValues>> testObserver = new TestObserver<>();
        webSocketConnection.parseStateDataObservable(TestData.testStateData).subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
    }

}