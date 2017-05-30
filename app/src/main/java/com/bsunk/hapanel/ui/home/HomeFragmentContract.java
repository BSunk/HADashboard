package com.bsunk.hapanel.ui.home;

import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.data.remote.WebSocketConnection;

import java.util.List;

/**
 * Created by bryan on 4/10/17.
 */

public interface HomeFragmentContract {

    interface View {

        void initializeRecyclerView(List<DeviceModel> deviceModels);
        void updateList(WebSocketConnection.DeviceListUpdateModel deviceListUpdateModel);
        void showHideLoading(boolean isHide);

    }

    interface Presenter {

        void subscribe(HomeFragmentContract.View view);
        void unSubscribe();
    }
}
