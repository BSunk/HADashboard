package com.bsunk.hapanel.data.model;

/**
 * Created by Bharat on 3/10/2017.
 */
public class DeviceModel {

    private String entity_id;
    private String state;
    private String last_changed;
    private String type;
    private String attributes;

    public DeviceModel(){}

    public DeviceModel(String entity_id, String state,
                       String last_changed, String attributes, String type) {
        this.entity_id = entity_id;
        this.state = state;
        this.last_changed = last_changed;
        this.attributes = attributes;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
