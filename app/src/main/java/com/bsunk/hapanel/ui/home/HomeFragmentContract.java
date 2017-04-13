package com.bsunk.hapanel.ui.home;

/**
 * Created by bryan on 4/10/17.
 */

public interface HomeFragmentContract {

    interface View {


    }

    interface Presenter {

        void subscribe(HomeFragmentContract.View view);
        void unSubscribe();

    }
}
