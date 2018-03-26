package com.hyjoy.databinding.adapter;

import android.support.annotation.LayoutRes;

/**
 * Recycler View Item 绑定
 * Created by hyjoy on 2018/3/16.
 */
public interface ViewMap<T> {
    @LayoutRes
    int layoutId(T t);
}
