package com.bsunk.hapanel.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bsunk.hapanel.data.model.DeviceModel;

/**
 * Created by bryan on 5/18/17.
 */
@Dao
public interface DeviceModelDao {

    @Insert
    public void insertDevice(DeviceModel... deviceModels);

    @Update
    public void updateDevice(DeviceModel... deviceModels);

    @Delete
    public void deleteDevice(DeviceModel... deviceModels);

    @Query("SELECT * FROM devicemodel")
    public DeviceModel[] loadAllDevices();

}
