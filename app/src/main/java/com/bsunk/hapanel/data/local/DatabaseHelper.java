package com.bsunk.hapanel.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bsunk.hapanel.data.model.DeviceModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
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
            DatabaseContract.HAPanel.COLUMN_ATTRIBUTES,
            DatabaseContract.HAPanel.COLUMN_POSITION,
            DatabaseContract.HAPanel.COLUMN_HIDE_FLAG,
            DatabaseContract.HAPanel.COLUMN_NOTIFY_FLAG,
            DatabaseContract.HAPanel.COLUMN_ALERT_FLAG
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

    public Observable<Void> bulkAddOrUpdateDevice(ArrayList<ContentValues> values) {
        return Observable.create(e -> {
            for(int i=0; i<values.size(); i++) {
                database.beginTransaction();
                String selectQuery = "SELECT * FROM " + DatabaseContract.HAPanel.TABLE_NAME + " WHERE " + DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?";
                Cursor cursor = database.rawQuery(selectQuery, new String[]{values.get(i).getAsString("entity_id")});
                database.setTransactionSuccessful();
                database.endTransaction();

                if (cursor.getCount() <= 0) {
                    long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values.get(i));
                    if (id != -1) {
                        Timber.v("Added device with id: " + id);
                    } else {
                        e.onError(new Throwable("Error inserting into database!"));
                    }
                } else {
                    String entityID = values.get(i).getAsString("entity_id");
                    long id = database.update(DatabaseContract.HAPanel.TABLE_NAME, values.get(i), DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?", new String[]{entityID});
                    if (id != -1) {
                        Timber.v("Updated device with entity id: " + values.get(i).getAsString("entity_id"));
                    } else {
                        e.onError(new Throwable("Error updating database!"));
                    }
                }
            }
            e.onComplete();
        });
    }

    public Observable<Void> addOrUpdateDevice(ContentValues values) {
        return Observable.create(e -> {
            database.beginTransaction();
                String selectQuery = "SELECT * FROM " + DatabaseContract.HAPanel.TABLE_NAME + " WHERE " + DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?";
                Cursor cursor = database.rawQuery(selectQuery, new String[]{values.getAsString("entity_id")});
                database.setTransactionSuccessful();
                database.endTransaction();

                if (cursor.getCount() <= 0) {
                    long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values);
                    if (id != -1) {
                        Timber.v("Added device with id: " + id);
                    } else {
                        e.onError(new Throwable("Error inserting into database!"));
                    }
                } else {
                    String entityID = values.getAsString("entity_id");
                    long id = database.update(DatabaseContract.HAPanel.TABLE_NAME, values, DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?", new String[]{entityID});
                    if (id != -1) {
                        Timber.v("Updated device with entity id: " + values.getAsString("entity_id"));
                    } else {
                        e.onError(new Throwable("Error updating database!"));
                    }
                }
            e.onComplete();
        });
    }

    private Observable<Long> addDevice(ContentValues values) {
        return Observable.create(e -> {
            long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values);
            if(id!=-1) {
                e.onNext(id);
                Timber.v("Added device with id: " + id);
            }
            else {
                e.onError(new Throwable("Error inserting into database!"));
            }
        });
    }

    public Observable<Long> updateDevice(ContentValues values) {
        String entityID = values.getAsString("entity_id");
        return Observable.create(e -> {
            long id = database.update(DatabaseContract.HAPanel.TABLE_NAME, values, DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?", new String[]{entityID});
            if(id!=-1) {
                e.onNext(id);
                Timber.v("Updated device with entity id: " + values.getAsString("entity_id"));
            }
            else {
                e.onError(new Throwable("Error updating database!"));
            }
        });
    }

    public Observable<Void> bulkAddDevices(ArrayList<ContentValues> list) {
        return Observable.create(e -> {
            for(int i=0; i<list.size(); i++) {
                ContentValues values = list.get(i);
                long id = database.insert(DatabaseContract.HAPanel.TABLE_NAME, null, values);
                Timber.v("Added device with id: " + id);
                if(id==-1) {e.onError(new Throwable("Error inserting into database!"));}
            }
            e.onComplete();
        });
    }

    public Observable<DeviceModel> getDevice(String entityID) {
        return Observable.create(e -> {
            Cursor cursor = database.query(DatabaseContract.HAPanel.TABLE_NAME,
                    allColumns,
                    DatabaseContract.HAPanel.COLUMN_ENTITY_ID + "=?",new String[]{entityID},null,null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                DeviceModel deviceModel = new DeviceModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
                Timber.v("Fetched device: " + deviceModel.toString());
                e.onNext(deviceModel);
                cursor.close();
            }
            else {
                e.onError(new Throwable("No device found with entityID: " + entityID));
            }

        });
    }

}
