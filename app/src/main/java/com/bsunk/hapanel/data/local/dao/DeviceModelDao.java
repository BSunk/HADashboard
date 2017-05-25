package com.bsunk.hapanel.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bsunk.hapanel.data.local.entity.DeviceModel;

/**
 * Created by bryan on 5/18/17.
 */
@Dao
public interface DeviceModelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertDevice(DeviceModel deviceModel);

    @Update
    public int updateDevice(DeviceModel deviceModel);

    @Delete
    public int deleteDevice(DeviceModel deviceModel);

    @Query("SELECT * FROM devicemodel WHERE type = :typeID ")
    public DeviceModel[] loadAllDevices(String typeID);

}
