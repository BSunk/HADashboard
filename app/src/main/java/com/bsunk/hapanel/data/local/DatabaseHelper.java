package com.bsunk.hapanel.data.local;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Bryan on 3/8/2017.
 */

public class DatabaseHelper {

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

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


    
}
