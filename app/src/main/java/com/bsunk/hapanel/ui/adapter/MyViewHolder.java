package com.bsunk.hapanel.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by bryan on 4/13/17.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public MyViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
    }
}