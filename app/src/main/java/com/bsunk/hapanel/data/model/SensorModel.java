package com.bsunk.hapanel.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SensorModel {

    @SerializedName("friendly_name")
    @Expose
    private String friendlyName;
    @SerializedName("unit_of_measurement")
    @Expose
    private String unitOfMeasurement;

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

}