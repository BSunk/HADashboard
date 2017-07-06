package com.bsunk.hapanel.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SensorModel {

    @SerializedName("friendly_name")
    @Expose
    var friendlyName: String? = null
    @SerializedName("unit_of_measurement")
    @Expose
    var unitOfMeasurement: String? = null

}