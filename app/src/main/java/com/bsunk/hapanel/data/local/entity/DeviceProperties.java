package com.bsunk.hapanel.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Bharat on 3/10/2017.
 */
@Entity(indices = {@Index(value = {"entity_id"}, unique = true)})
public class DeviceProperties {

    @PrimaryKey(autoGenerate = true) private int position;
    private String entity_id;
    private int hide;

    @Ignore
    public DeviceProperties(){}

    public DeviceProperties(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
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

}
