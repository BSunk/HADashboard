package com.bsunk.hapanel.ui.main;

import com.bsunk.hapanel.ui.base.BasePresenter;
import com.bsunk.hapanel.ui.base.BaseView;

/**
 * Created by Bharat on 3/8/2017.
 */

public interface MainActivityContract {

    interface View extends BaseView<Presenter> {

        void startConnectionService();

    }

    interface Presenter extends BasePresenter {

        void connectToServer();

    }
}
