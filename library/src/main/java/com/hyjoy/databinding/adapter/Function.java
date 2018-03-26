package com.hyjoy.databinding.adapter;

import android.view.View;

/**
 * RecyclerView Adapter
 * Created by hyjoy on 2018/3/16.
 */
public interface Function<T> {

    void call(View view, T t);
}
