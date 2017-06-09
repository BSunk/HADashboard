package com.bsunk.hapanel.ui.groups;

/**
 * Created by bryan on 6/3/17.
 */

public interface GroupsContract {
    interface View {


    }

    interface Presenter {

        void subscribe(GroupsContract.View view);

    }
}
