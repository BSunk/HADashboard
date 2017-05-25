package com.bsunk.hapanel.data.model;

/**
 * Created by bryan on 5/24/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LightModel {

    @SerializedName("brightness")
    @Expose
    private Integer brightness;
    @SerializedName("color_temp")
    @Expose
    private Integer colorTemp;
    @SerializedName("effect")
    @Expose
    private String effect;
    @SerializedName("effect_list")
    @Expose
    private List<String> effectList = null;
    @SerializedName("friendly_name")
    @Expose
    private String friendlyName;
    @SerializedName("rgb_color")
    @Expose
    private List<Integer> rgbColor = null;
    @SerializedName("supported_features")
    @Expose
    private Integer supportedFeatures;
    @SerializedName("white_value")
    @Expose
    private Integer whiteValue;
    @SerializedName("xy_color")
    @Expose
    private List<Double> xyColor = null;

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public Integer getColorTemp() {
        return colorTemp;
    }

    public void setColorTemp(Integer colorTemp) {
        this.colorTemp = colorTemp;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public List<String> getEffectList() {
        return effectList;
    }

    public void setEffectList(List<String> effectList) {
        this.effectList = effectList;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public List<Integer> getRgbColor() {
        return rgbColor;
    }

    public void setRgbColor(List<Integer> rgbColor) {
        this.rgbColor = rgbColor;
    }

    public Integer getSupportedFeatures() {
        return supportedFeatures;
    }

    public void setSupportedFeatures(Integer supportedFeatures) {
        this.supportedFeatures = supportedFeatures;
    }

    public Integer getWhiteValue() {
        return whiteValue;
    }

    public void setWhiteValue(Integer whiteValue) {
        this.whiteValue = whiteValue;
    }

    public List<Double> getXyColor() {
        return xyColor;
    }

    public void setXyColor(List<Double> xyColor) {
        this.xyColor = xyColor;
    }

}