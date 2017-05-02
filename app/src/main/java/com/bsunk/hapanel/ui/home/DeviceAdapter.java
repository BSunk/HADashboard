package com.bsunk.hapanel.ui.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.model.DeviceModel;

import java.util.ArrayList;

import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.LIGHT_TYPE;

/**
 * Created by bryan on 4/13/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<DeviceModel> devices;

    DeviceAdapter(ArrayList<DeviceModel> devices) {
        this.devices = devices;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        return new MyViewHolder(binding);
    }

    public void onBindViewHolder(MyViewHolder holder,
                                 int position) {
        Object obj = getObjForPosition(position);
        holder.bind(obj);
    }

    private Object getObjForPosition(int position) {
        return devices.get(position);
    }

    @Override
    public int getItemViewType(int position) {
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