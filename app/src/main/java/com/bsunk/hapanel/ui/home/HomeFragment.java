package com.bsunk.hapanel.ui.home;


import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.data.DataManager;
import com.bsunk.hapanel.data.model.DeviceModel;
import com.bsunk.hapanel.data.remote.WebSocketConnection;
import com.bsunk.hapanel.databinding.FragmentHomeBinding;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;
import com.bsunk.hapanel.ui.adapter.DeviceAdapter;
import com.bsunk.hapanel.ui.adapter.helper.OnStartDragListener;
import com.bsunk.hapanel.ui.adapter.helper.SimpleItemTouchHelperCallback;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends LifecycleFragment implements HomeFragmentContract.View, OnStartDragListener {

    private ActivityComponent mActivityComponent;
    private RecyclerView devicesRecyclerView;
    private DeviceAdapter adapter;
    private ProgressBar progressBar;
    private ItemTouchHelper mItemTouchHelper;
    private boolean editMode = false;

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
        progressBar = binding.loading;

        //Edit button on click
        getActivity().findViewById(R.id.edit_iv).setOnClickListener(this::setEditModeVisibility);

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
    public void initializeRecyclerView(List<DeviceModel> deviceModels) {
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        devicesRecyclerView.setLayoutManager(sglm);
        adapter = new DeviceAdapter(deviceModels, this, dataManager);
        devicesRecyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(devicesRecyclerView);
    }

    @Override
    public void updateList(WebSocketConnection.DeviceListUpdateModel deviceListUpdateModel) {
        if(adapter!=null) {
            adapter.setItems(deviceListUpdateModel.list, deviceListUpdateModel.updateID);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @Override
    public void showHideLoading(boolean isHide) {
        if(isHide) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void setEditModeVisibility(View v) {
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);

        if(!editMode) {
            v.startAnimation(rotate);
            ((ImageView) v).setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            editMode = true;
        }
        else {
            v.startAnimation(rotate);
            ((ImageView) v).setColorFilter(getResources().getColor(android.R.color.black));
            editMode = false;
        }
        if(adapter!=null) {
            adapter.setVisibilityEditMode(editMode);
        }
    }

}
