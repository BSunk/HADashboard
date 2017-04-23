package com.bsunk.hapanel.ui.home;

import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.ui.MyBaseAdapter;

import java.util.ArrayList;

import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.LIGHT_TYPE;

/**
 * Created by bryan on 4/13/17.
 */

public class DeviceAdapter extends MyBaseAdapter{

    private ArrayList<DeviceModel> devices;

    public DeviceAdapter(ArrayList<DeviceModel> devices) {
        this.devices = devices;
    }

    @Override
    protected Object getObjForPosition(int position) {
        return devices.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        if(devices.get(position).getType().equals(LIGHT_TYPE)) {
            return R.layout.light_item;
        }
        else {
            return R.layout.light_item;
        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
