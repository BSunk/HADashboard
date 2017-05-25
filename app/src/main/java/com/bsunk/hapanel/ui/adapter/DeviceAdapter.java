package com.bsunk.hapanel.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.local.entity.DeviceModel;
import com.bsunk.hapanel.data.model.LightModel;
import com.google.gson.Gson;

import timber.log.Timber;

import static android.widget.Adapter.IGNORE_ITEM_VIEW_TYPE;
import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.LIGHT_TYPE;

/**
 * Created by bryan on 4/13/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {

    private DeviceModel[] devices;

    public DeviceAdapter(DeviceModel[] devices) {
        this.devices = devices;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new MyViewHolder(binding);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gson gson = new Gson();

        switch (devices[position].getType()) {
            case LIGHT_TYPE:
                LightModel lightModel = gson.fromJson(devices[position].getAttributes(), LightModel.class);
                Timber.v(lightModel.getFriendlyName());
                holder.bind(lightModel, devices[position]);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (devices[position].getType()) {
            case LIGHT_TYPE:
                return  R.layout.light_item;
            default:
                return IGNORE_ITEM_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return devices.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Object obj, Object obj2) {
            binding.setVariable(BR.attributes, obj);
            binding.setVariable(BR.device, obj2);
            binding.executePendingBindings();
        }
    }
}