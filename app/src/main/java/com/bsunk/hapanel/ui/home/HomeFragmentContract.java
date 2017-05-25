package com.bsunk.hapanel.ui.home;

import com.bsunk.hapanel.data.local.entity.DeviceModel;

/**
 * Created by bryan on 4/10/17.
 */

public interface HomeFragmentContract {

    interface View {

        void initializeRecyclerView(DeviceModel[] deviceModels);

    }

    interface Presenter {

        void subscribe(HomeFragmentContract.View view);
        void unSubscribe();
        void initDeviceList();

    }
}
