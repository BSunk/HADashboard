package com.bsunk.hapanel.ui.main

/**
 * Created by Bharat on 3/8/2017.
 */

interface MainActivityContract {

    interface View {

        fun startStopConnectionService(shouldStart: Boolean)
        fun setTitle(name: String)
        fun setConnectionImage(event: Int)
        fun keepScreenOn(shouldKeepOn: Boolean)

    }

    interface Presenter {

        fun subscribe(view: MainActivityContract.View)
        fun unSubscribe()
        fun setView(view: MainActivityContract.View)
        fun initToolbarTitle()

    }
}
