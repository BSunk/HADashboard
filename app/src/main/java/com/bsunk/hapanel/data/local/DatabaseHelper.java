package com.bsunk.hapanel.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bsunk.hapanel.data.model.DeviceModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Bryan on 3/8/2017.
 */

public class DatabaseHelper {

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    private static final String[] allColumns = {
            DatabaseContract.HAPanel.COLUMN_ENTITY_ID,
            DatabaseContract.HAPanel.COLUMN_STATE,
            DatabaseContract.HAPanel.COLUMN_LAST_CHANGED,
            DatabaseContract.HAPanel.COLUMN_ATTRIBUTES
    };

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        dbhandler = dbOpenHelper;
    }

    public void open(){
        Timber.v("Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        Timber.v("Database Closed");
        dbhandler.close();
    }

    public void addDevice(DeviceModel deviceModel) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.HAPanel.COLUMN_ENTITY_ID, deviceModel.getEntity_id());
        values.put(DatabaseContract.HAPanel.COLUMN_STATE, deviceModel.getState());
        values.put(DatabaseContract.HAPanel.COLUMN_ATTRIBUTES, deviceModel.getAttributes());
        values.put(DatabaseContract.HAPanel.COLUMN_LAST_CHANGED, deviceModel.getLast_changed());
        values.put(DatabaseContract.HAPanel.COLUMN_SHOW_FLAG, true);
        long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values);
        Timber.v("Adding device: " + id);
    }

    public DeviceModel getDevice(String entityID) {
        Cursor cursor = database.query(DatabaseContract.HAPanel.TABLE_NAME,
                allColumns,
                DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?",new String[]{entityID},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DeviceModel deviceModel = new DeviceModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        Timber.v("Fetched device: " + deviceModel.toString());
        return deviceModel;
    }
}
