package com.bsunk.hapanel.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bryan on 5/14/17.
 */
@Entity(foreignKeys = @ForeignKey(entity = DeviceModel.class, parentColumns = "entity_id", childColumns = "entity_id"))

public class LightModel {

    @PrimaryKey private String entity_id;
    private String friendlyName;
    private int brightness;
    private int colorBlue;
    private int colorRed;
    private int colorGreen;

    public LightModel(){}

    public LightModel(String attributes, String entity_id) {
        this.entity_id = entity_id;
        setAttrsParams(attributes);
    }

    private void setAttrsParams(String attributes) {
        try {
            JSONObject attributesObject = new JSONObject(attributes);
            if(attributesObject.has("brightness")) {
                double brightnessRaw = (Integer.valueOf(attributesObject.getString("brightness"))/255.0) * 100.0;
                brightness = (int) brightnessRaw;
            }
            if(attributesObject.has("friendly_name")) {
                friendlyName = attributesObject.getString("friendly_name");
            }
            if(attributesObject.has("rgb_color")) {
                JSONArray array = attributesObject.getJSONArray("rgb_color");
                colorRed = array.getInt(0);
                colorGreen = array.getInt(1);
                colorBlue = array.getInt(2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getColorBlue() {
        return colorBlue;
    }

    public int getColorRed() {
        return colorRed;
    }

    public int getColorGreen() {
        return colorGreen;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public void setColorBlue(int colorBlue) {
        this.colorBlue = colorBlue;
    }

    public void setColorRed(int colorRed) {
        this.colorRed = colorRed;
    }

    public void setColorGreen(int colorGreen) {
        this.colorGreen = colorGreen;
    }

}
