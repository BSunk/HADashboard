package com.bsunk.hapanel.ui.home;

import com.bsunk.hapanel.data.model.DeviceModel;

import java.util.ArrayList;

/**
 * Created by bryan on 4/10/17.
 */

public interface HomeFragmentContract {

    interface View {

        void initializeRecyclerView(ArrayList<DeviceModel> deviceModels);

    }

    interface Presenter {

        void subscribe(HomeFragmentContract.View view);
        void unSubscribe();
        void initDeviceList();

    }
}
