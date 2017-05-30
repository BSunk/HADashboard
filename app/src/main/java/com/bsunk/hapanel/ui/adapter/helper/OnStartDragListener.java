package com.bsunk.hapanel.ui.adapter.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by bryan on 5/30/17.
 */

public interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

}