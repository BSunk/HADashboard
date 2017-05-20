package com.bsunk.hapanel.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.bsunk.hapanel.data.model.LightModel;

/**
 * Created by bryan on 5/19/17.
 */
@Dao
public interface LightModelDao {

    @Insert
    public void insertLightDevice(LightModel lightModel);

    @Update
    public void updateLightDevice(LightModel lightModel);

    @Delete
    public void deleteLightDevice(LightModel lightModel);

}
