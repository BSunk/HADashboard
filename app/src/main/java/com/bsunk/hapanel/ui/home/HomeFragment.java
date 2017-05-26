package com.bsunk.hapanel.ui.home;


import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.local.entity.DeviceModel;
import com.bsunk.hapanel.databinding.FragmentHomeBinding;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;
import com.bsunk.hapanel.ui.adapter.DeviceAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends LifecycleFragment implements HomeFragmentContract.View {

    private ActivityComponent mActivityComponent;
    private RecyclerView devicesRecyclerView;
    private DeviceAdapter adapter;

    @Inject
    DataManager dataManager;
    @Inject
    HomeFragmentPresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        devicesRecyclerView = binding.devicesRv;

        activityComponent().inject(this);
        presenter.subscribe(this);
        presenter.initDeviceList();

        return binding.getRoot();
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

    @Override
    public void initializeRecyclerView(List<DeviceModel> deviceModels) {
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        devicesRecyclerView.setLayoutManager(sglm);
        adapter = new DeviceAdapter(deviceModels);
        devicesRecyclerView.setAdapter(adapter);

        //Data set changed. Update RecyclerView
        dataManager.getDeviceRepository().getAllDevicesLiveData().observe(this, deviceModelList -> {
            adapter.setItems(deviceModelList);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

}
