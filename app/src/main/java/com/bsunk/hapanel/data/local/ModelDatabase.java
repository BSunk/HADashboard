package com.bsunk.hapanel.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bsunk.hapanel.data.local.dao.DeviceModelDao;
import com.bsunk.hapanel.data.local.entity.DeviceProperties;

/**
 * Created by bryan on 5/19/17.
 */

@Database(entities = {DeviceProperties.class}, version = 10)
public abstract class ModelDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "ha.db";

    public abstract DeviceModelDao deviceModelDao();

}
