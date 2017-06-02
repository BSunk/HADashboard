package com.bsunk.hapanel.ui.main;

/**
 * Created by Bharat on 3/8/2017.
 */

public interface MainActivityContract {

    interface View {

        void startStopConnectionService(boolean shouldStart);
        void setTitle(String name);
        void setConnectionImage(int event);
        void keepScreenOn(boolean shouldKeepOn);

    }

    interface Presenter {

        void subscribe(MainActivityContract.View view);
        void unSubscribe();
        void setView(MainActivityContract.View view);
        void initToolbarTitle();

    }
}
