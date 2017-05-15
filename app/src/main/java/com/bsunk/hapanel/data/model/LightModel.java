package com.bsunk.hapanel.data.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bryan on 5/14/17.
 */

public class LightModel extends DeviceModel {

    private String friendlyName;
    private int brightness;
    private int colorBlue;
    private int colorRed;
    private int colorGreen;

    public LightModel(String entity_id, String state,
               String last_changed, String attributes,
               String type, int position, int hide) {

        super(entity_id, state, last_changed, attributes, type, position, hide);

        try {
            JSONObject attributesObject = new JSONObject(attributes);
            if(attributesObject.has("brightness")) {
                double brightnessRaw = (Integer.valueOf(attributesObject.getString("brightness"))/255.0) * 100.0;
                brightness = (int) brightnessRaw;
            }
            if(attributesObject.has("friendly_name")) {
                friendlyName = attributesObject.getString("friendly_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getColorBlue() {
        return colorBlue;
    }

    public void setColorBlue(int colorBlue) {
        this.colorBlue = colorBlue;
    }

    public int getColorRed() {
        return colorRed;
    }

    public void setColorRed(int colorRed) {
        this.colorRed = colorRed;
    }

    public int getColorGreen() {
        return colorGreen;
    }

    public void setColorGreen(int colorGreen) {
        this.colorGreen = colorGreen;
    }

}
