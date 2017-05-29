package com.bsunk.hapanel.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BinarySensorModel {

    @SerializedName("device_class")
    @Expose
    var deviceClass: String? = null
    @SerializedName("friendly_name")
    @Expose
    var friendlyName: String? = null

}