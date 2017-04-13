package com.bsunk.hapanel.ui.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsunk.hapanel.HAApplication;
import com.bsunk.hapanel.R;
import com.bsunk.hapanel.databinding.FragmentHomeBinding;
import com.bsunk.hapanel.di.components.ActivityComponent;
import com.bsunk.hapanel.di.components.DaggerActivityComponent;
import com.bsunk.hapanel.di.modules.ActivityModule;

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
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_home);
        activityComponent().inject(this);
        presenter.subscribe(this);

        return inflater.inflate(R.layout.fragment_home, container, false);
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

}
