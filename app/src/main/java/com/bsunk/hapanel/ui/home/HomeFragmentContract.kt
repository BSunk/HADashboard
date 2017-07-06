package com.bsunk.hapanel.ui.home

import com.bsunk.hapanel.data.model.DeviceModel
import com.bsunk.hapanel.data.remote.WebSocketConnection

/**
 * Created by bryan on 4/10/17.
 */

interface HomeFragmentContract {

    interface View {

        fun initializeRecyclerView(deviceModels: List<DeviceModel>)
        fun updateList(deviceListUpdateModel: WebSocketConnection.DeviceListUpdateModel)
        fun showHideLoading(isHide: Boolean)

    }

    interface Presenter {

        fun subscribe(view: HomeFragmentContract.View)
        fun unSubscribe()
    }
}
