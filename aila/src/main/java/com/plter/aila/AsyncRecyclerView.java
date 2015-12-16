package com.plter.aila;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by plter on 12/13/15.
 */
public class AsyncRecyclerView extends RecyclerView {
    public AsyncRecyclerView(Context context) {
        super(context);

        onInit();
    }

    public AsyncRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        onInit();
    }

    public AsyncRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        onInit();
    }

    private void onInit() {
    }
}
