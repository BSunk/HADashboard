package com.bsunk.hapanel.ui.groups;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment implements GroupsContract.View {

    private ActivityComponent mActivityComponent;

    @Inject
    GroupsPresenter presenter;


    public GroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        activityComponent().inject(this);
        presenter.subscribe(this);


        return inflater.inflate(R.layout.fragment_groups, container, false);
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(getActivity()))
                    .applicationComponent(HAApplication.get(getActivity()).getApplicationComponent())
                    .build();
        }
        return mActivityComponent;
    }

    public void initializeList(List<DeviceModel> deviceModels) {

    }

}
