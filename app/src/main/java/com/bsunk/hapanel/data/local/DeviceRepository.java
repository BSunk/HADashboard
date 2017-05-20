package com.bsunk.hapanel.data.local;

import com.bsunk.hapanel.data.local.entity.DeviceModel;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by bryan on 5/19/17.
 */

public class DeviceRepository {

    private final ModelDatabase modelDatabase;

    @Inject
    public DeviceRepository(ModelDatabase modelDatabase) {
        this.modelDatabase = modelDatabase;
    }

    public long addDevice(DeviceModel deviceModel) {
        return modelDatabase.deviceModelDao().insertDevice(deviceModel);
    }

    public int updateDevice(DeviceModel deviceModel) {
        return modelDatabase.deviceModelDao().updateDevice(deviceModel);
    }

    public Completable deleteDevice(DeviceModel deviceModel) {
        return Completable.fromAction(() -> modelDatabase.deviceModelDao().deleteDevice(deviceModel));
    }

    public DeviceModel[] getDevices() {
        return modelDatabase.deviceModelDao().loadAllDevices();
    }

}
