package com.bsunk.hapanel.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bsunk.hapanel.data.local.entity.DeviceProperties;

import java.util.List;

/**
 * Created by bryan on 5/18/17.
 */
@Dao
public interface DeviceModelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertDevice(DeviceProperties deviceProperties);

    @Update
    public int updateDevice(DeviceProperties deviceProperties);

    @Delete
    public int deleteDevice(DeviceProperties deviceProperties);

    @Query("SELECT * FROM deviceproperties")
    public List<DeviceProperties> loadAllDevices();

}
