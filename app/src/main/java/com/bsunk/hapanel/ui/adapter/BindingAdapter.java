package com.bsunk.hapanel.ui.adapter;

import com.triggertrap.seekarc.SeekArc;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bryan on 5/9/17.
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter("setBrightness")
    public static void setBrightness(SeekArc seekArc, String attributes) {
        try {
            JSONObject attributesObject = new JSONObject(attributes);
            if(attributesObject.has("brightness")) {
                double percentBrightness = (Integer.valueOf(attributesObject.getString("brightness"))/255.0) * 100.0;
                seekArc.setProgress((int) percentBrightness);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
