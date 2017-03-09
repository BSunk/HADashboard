package com.bsunk.hapanel.data.local;

import javax.inject.Inject;

/**
 * Created by Bryan on 3/8/2017.
 */

public class DatabaseHelper {

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        //mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper);
    }
    
}
