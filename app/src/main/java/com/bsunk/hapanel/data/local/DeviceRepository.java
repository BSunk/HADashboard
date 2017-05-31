package com.bsunk.hapanel.data.local;

import com.bsunk.hapanel.data.local.entity.DeviceProperties;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by bryan on 5/19/17.
 */

public class DeviceRepository {

    private final ModelDatabase modelDatabase;

    @Inject
    public DeviceRepository(ModelDatabase modelDatabase) {
        this.modelDatabase = modelDatabase;
    }

    public long addDevice(DeviceProperties lightModel) {
        return modelDatabase.deviceModelDao().insertDevice(lightModel);
    }

    public int updateDevice(DeviceProperties deviceProperties) {
        return modelDatabase.deviceModelDao().updateDevice(deviceProperties);
    }

    public Observable<List<DeviceProperties>> getAllDevices() {
        return Observable.create((ObservableEmitter<List<DeviceProperties>> e) -> e.onNext(modelDatabase.deviceModelDao().loadAllDevices()));
    }

}
