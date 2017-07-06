package com.bsunk.hapanel.data.model

/**
 * Created by bryan on 5/24/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LightModel {

    @SerializedName("brightness")
    @Expose
    var brightness: Int? = null
    @SerializedName("color_temp")
    @Expose
    var colorTemp: Int? = null
    @SerializedName("effect")
    @Expose
    var effect: String? = null
    @SerializedName("effect_list")
    @Expose
    var effectList: List<String>? = null
    @SerializedName("friendly_name")
    @Expose
    var friendlyName: String? = null
    @SerializedName("rgb_color")
    @Expose
    var rgbColor: List<Int>? = null
    @SerializedName("white_value")
    @Expose
    var whiteValue: Int? = null
    @SerializedName("xy_color")
    @Expose
    var xyColor: List<Double>? = null

}