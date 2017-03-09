package com.bsunk.hadashboard.ui.main;

import com.bsunk.hadashboard.ui.base.BasePresenter;
import com.bsunk.hadashboard.ui.base.BaseView;

/**
 * Created by Bharat on 3/8/2017.
 */

public interface MainActivityContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void connectToServer();

    }
}
