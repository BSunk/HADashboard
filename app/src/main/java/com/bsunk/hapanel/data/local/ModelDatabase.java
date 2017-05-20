package com.bsunk.hapanel.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bsunk.hapanel.data.local.dao.DeviceModelDao;
import com.bsunk.hapanel.data.local.dao.LightModelDao;
import com.bsunk.hapanel.data.local.entity.DeviceModel;
import com.bsunk.hapanel.data.local.entity.LightModel;

/**
 * Created by bryan on 5/19/17.
 */

@Database(entities = {DeviceModel.class, LightModel.class}, version = 2)
public abstract class ModelDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "ha.db";

    public abstract DeviceModelDao deviceModelDao();
    public abstract LightModelDao lightModelDao();

}
