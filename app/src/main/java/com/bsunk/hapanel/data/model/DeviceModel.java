package com.bsunk.hapanel.data.model;

import android.support.annotation.NonNull;

/**
 * Created by Bharat on 3/10/2017.
 */

public class DeviceModel {
    private String entity_id;
    private String state;
    private String last_changed;
    private String attributes;

    public DeviceModel(@NonNull String entity_id, String state, String last_changed, String attributes) {
        this.entity_id = entity_id;
        this.state = state;
        this.last_changed = last_changed;
        this.attributes = attributes;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLast_changed() {
        return last_changed;
    }

    public void setLast_changed(String last_changed) {
        this.last_changed = last_changed;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "entity_id='" + entity_id + '\'' +
                ", state='" + state + '\'' +
                ", last_changed='" + last_changed + '\'' +
                ", attributes='" + attributes + '\'' +
                '}';
    }
}
