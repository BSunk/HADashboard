package com.bsunk.hapanel.ui.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.databinding.FragmentHomeBinding;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFragmentContract.View {

    private ActivityComponent mActivityComponent;
    private FragmentHomeBinding binding;

    @Inject
    HomeFragmentPresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        activityComponent().inject(this);
        presenter.subscribe(this);

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
    public void initializeRecyclerView(ArrayList<DeviceModel> deviceModels) {
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.devicesRv.setLayoutManager(sglm);
        binding.devicesRv.setAdapter(new DeviceAdapter(deviceModels));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

}
