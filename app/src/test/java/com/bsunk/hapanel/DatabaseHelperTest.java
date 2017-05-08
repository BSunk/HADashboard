package com.bsunk.hapanel;

import android.content.ContentValues;

import com.bsunk.hapanel.data.local.DatabaseContract;
import com.bsunk.hapanel.data.local.DatabaseHelper;
import com.bsunk.hapanel.data.local.DbOpenHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.reactivex.observers.TestObserver;
import static org.junit.Assert.*;

/**
 * Created by bryan on 4/29/17.
 */
//@Config(constants = BuildConfig.class, sdk = 25)
//@RunWith(RobolectricTestRunner.class)
public class DatabaseHelperTest extends BaseTest {

//    private DatabaseHelper databaseHelper;
//    DbOpenHelper dbOpenHelper;
//
//    @Before
//    public void setUp() {
//        dbOpenHelper = new DbOpenHelper(RuntimeEnvironment.application);
//        databaseHelper = new DatabaseHelper(dbOpenHelper);
//    }

//    @Test
//    public void shouldAddDevice() {
//        ContentValues values = new ContentValues();
//        values.put(DatabaseContract.HAPanel.COLUMN_ENTITY_ID, "light.bed_light");
//        values.put(DatabaseContract.HAPanel.COLUMN_STATE, "off");
//        values.put(DatabaseContract.HAPanel.COLUMN_ATTRIBUTES, "{\"friendly_name\":\"Bed Light\",\"supported_features\":151}");
//        values.put(DatabaseContract.HAPanel.COLUMN_LAST_CHANGED, "2017-04-29T02:10:57.875377+00:00");
//        values.put(DatabaseContract.HAPanel.COLUMN_TYPE, "light");
//
//        TestObserver<Long> testObserver = new TestObserver<>();
//        databaseHelper.addDevice(values).subscribeWith(testObserver);
//        //testObserver.assertNoErrors();
//    }




}