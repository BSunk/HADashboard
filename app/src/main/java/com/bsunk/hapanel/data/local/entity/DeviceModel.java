package com.bsunk.hapanel.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bharat on 3/10/2017.
 */
@Entity(indices = {@Index(value = {"entity_id"}, unique = true)})
public class DeviceModel {

    @PrimaryKey private String entity_id;
    private String state;
    private String last_changed;
    private int position;
    private int hide;
    private String type;
    private String attributes;

    @Ignore
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getHide() {
        return hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
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
