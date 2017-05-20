package com.bsunk.hapanel.data;

import com.bsunk.hapanel.data.local.DeviceRepository;
import com.bsunk.hapanel.data.local.SharedPrefHelper;

import javax.inject.Inject;

/**
 * Created by Bharat on 3/8/2017.
 */

public class DataManager {

    private final SharedPrefHelper sharedPrefHelper;
    private final DeviceRepository deviceRepository;


    @Inject
    public DataManager(SharedPrefHelper sharedPrefHelper, DeviceRepository deviceRepository) {
        this.sharedPrefHelper = sharedPrefHelper;
        this.deviceRepository = deviceRepository;
    }

    public SharedPrefHelper getSharedPrefHelper() {
        return sharedPrefHelper;
    }

    public DeviceRepository getDeviceRepository() {return deviceRepository;}

}
