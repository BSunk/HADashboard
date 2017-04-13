package com.bsunk.hapanel.ui.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.databinding.LightItemBinding;

import java.util.ArrayList;

import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.LIGHT_TYPE;
import static com.bsunk.hapanel.data.Constants.DEVICE_TYPE.SENSOR_TYPE;

/**
 * Created by bryan on 4/13/17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<DeviceModel> devices;

    public DeviceAdapter(Context context, ArrayList<DeviceModel> devices) {
        mContext = context;
        this.devices = devices;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==SENSOR_TYPE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.light_item, parent, false);
            return new LightViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.light_item, parent, false);
            return new LightViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DeviceModel deviceModel = devices.get(position);
        if(holder.getItemViewType()==SENSOR_TYPE) {

        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public int getItemViewType(int position) {
        String type = devices.get(position).getType();
        if (type.equals("sensor")) {
            return SENSOR_TYPE;
        }
        else if(type.equals("light")) {
            return LIGHT_TYPE;
        }
        return 0;
    }

    public class LightViewHolder extends RecyclerView.ViewHolder {
        private LightItemBinding binding;

        public LightViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public LightItemBinding getBinding() {
            return binding;
        }
    }


}
