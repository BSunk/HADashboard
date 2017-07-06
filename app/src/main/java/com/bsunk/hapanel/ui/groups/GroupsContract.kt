package com.bsunk.hapanel.ui.groups

/**
 * Created by bryan on 6/3/17.
 */

interface GroupsContract {
    interface View

    interface Presenter {

        fun subscribe(view: GroupsContract.View)

    }
}
