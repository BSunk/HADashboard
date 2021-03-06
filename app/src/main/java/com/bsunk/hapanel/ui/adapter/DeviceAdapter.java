package com.bsunk.hapanel.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.model.BinarySensorModel;
import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.data.model.LightModel;
import com.bsunk.hapanel.data.model.SensorModel;
import com.bsunk.hapanel.ui.adapter.helper.ItemTouchHelperAdapter;
import com.bsunk.hapanel.ui.adapter.helper.ItemTouchHelperViewHolder;
import com.bsunk.hapanel.ui.adapter.helper.OnStartDragListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static android.widget.Adapter.IGNORE_ITEM_VIEW_TYPE;
import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.BINARY_SENSOR_TYPE;
import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.LIGHT_TYPE;
import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.SENSOR_TYPE;

/**
 * Created by bryan on 4/13/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private List<DeviceModel> devices;
    private final OnStartDragListener mDragStartListener;
    private DataManager dataManager;
    private boolean isEditMode;

    @Inject
    public DeviceAdapter(List<DeviceModel> devices, OnStartDragListener dragStartListener, DataManager dataManager) {
        this.devices = devices;
        mDragStartListener = dragStartListener;
        this.dataManager = dataManager;
        saveDevicePositions();
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new MyViewHolder(binding);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        Gson gson = new Gson();

        switch (devices.get(position).getType()) {
            case LIGHT_TYPE:
                LightModel lightModel = gson.fromJson(devices.get(position).getAttributes(), LightModel.class);
                holder.bind(lightModel, devices.get(position));
                break;
            case SENSOR_TYPE:
                SensorModel sensorModel = gson.fromJson(devices.get(position).getAttributes(), SensorModel.class);
                holder.bind(sensorModel, devices.get(position));
                break;
            case BINARY_SENSOR_TYPE:
                BinarySensorModel binarySensorModel = gson.fromJson(devices.get(position).getAttributes(), BinarySensorModel.class);
                holder.bind(binarySensorModel, devices.get(position));
                break;
        }

        if(isEditMode) holder.binding.getRoot().findViewById(R.id.device_properties_layout).setVisibility(View.VISIBLE);
        else holder.binding.getRoot().findViewById(R.id.device_properties_layout).setVisibility(View.GONE);

        holder.binding.getRoot().findViewById(R.id.drag_handle).setOnTouchListener((v, event) -> {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            return false;
        });
    }

    @Override
    public int getItemViewType(int position) {
        switch (devices.get(position).getType()) {
            case LIGHT_TYPE:
                return  R.layout.light_item;
            case SENSOR_TYPE:
                return R.layout.sensor_item;
            case BINARY_SENSOR_TYPE:
                return R.layout.binary_sensor_item;
            default:
                return IGNORE_ITEM_VIEW_TYPE;
        }
    }

    public void setItems(List<DeviceModel> devices, int updateID) {
        this.devices = devices;
        notifyItemChanged(updateID);
    }

    public void setVisibilityEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
        notifyItemRangeChanged(0, devices.size());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(devices, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(devices, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        saveDevicePositions();
        return true;
    }

    private void saveDevicePositions() {
        List<String> listOfEntityIDs = new ArrayList<>();
        for(DeviceModel deviceModel: devices) {
            listOfEntityIDs.add(deviceModel.getEntity_id());
        }

        Gson gson = new Gson();
        dataManager.getSharedPrefHelper().putHomeDevicesList(gson.toJson(listOfEntityIDs));
    }

    @Override
    public void onItemDismiss(int position) {
        devices.remove(position);
        notifyItemRemoved(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
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

        @Override
        public void onItemSelected() {
            //itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            //itemView.setBackgroundColor(0);
        }
    }
}