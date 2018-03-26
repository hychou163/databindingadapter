package com.hyjoy.databinding.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class DataBindRecycleViewAdapter<T> extends RecyclerView.Adapter<DataBindRecycleViewAdapter.RVViewHolder> {
    private int variableId;
    private int layoutId;

    public interface BindCreate<R extends ViewDataBinding> {
        void change(R t);
    }

    public interface BindTo<R extends ViewDataBinding, T> {
        void bindTo(R r, T t);
    }

    private SparseArray<Function<T>> event;
    private SparseArray<Object> data;
    private Context context;
    private List<T> list;
    private ViewMap<T> viewMap;

    public void addEvent(int variableId, Function<T> function) {
        event.put(variableId, function);
    }

    public void addData(int variableId, Object data) {
        this.data.put(variableId, data);
        notifyDataSetChanged();
    }

    private BindCreate bindCreate;
    private BindTo bindTo;

    public <R extends ViewDataBinding> void setBindTo(BindTo<R, T> bindTo) {
        this.bindTo = bindTo;
    }

    public <R extends ViewDataBinding> void setBindCreate(BindCreate<R> bindCreate) {
        this.bindCreate = bindCreate;
    }

    public DataBindRecycleViewAdapter(Context context, @LayoutRes int layoutId, int variableId) {
        this.context = context;
        this.list = new ArrayList<>();
        event = new SparseArray<>();
        data = new SparseArray<>();
        this.layoutId = layoutId;
        this.variableId = variableId;
    }

    public DataBindRecycleViewAdapter(Context context, int variableId, ViewMap<T> viewMap) {
        this.context = context;
        this.list = new ArrayList<>();
        event = new SparseArray<>();
        data = new SparseArray<>();
        this.viewMap = viewMap;
        this.variableId = variableId;
    }

    public void notifyDataChanged(T t) {
        if (list.contains(t)) {
            notifyItemChanged(list.indexOf(t));
        }
    }

    private int dataVersion = 0;


    public void setData(final List<T> update) {

        if (update == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = update;
        }
        notifyDataSetChanged();
    }


    @Override
    public DataBindRecycleViewAdapter.RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RVViewHolder hold = new RVViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), viewType, parent, false));
        if (bindCreate != null) {
            bindCreate.change(hold.binding);
        }
        return hold;
    }

    public void onBindViewHolder(@NonNull DataBindRecycleViewAdapter.RVViewHolder holder, int position) {
        holder.bindTo(list.get(position));
        if (bindTo != null) {
            bindTo.bindTo(holder.binding, list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (viewMap != null) {
            return viewMap.layoutId(list.get(position));
        }
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RVViewHolder extends com.hyjoy.databinding.adapter.RVViewHolder {
        ViewDataBinding binding;

        RVViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindTo(Object value) {
            binding.setVariable(variableId, value);
            for (int i = 0; i < event.size(); i++) {
                binding.setVariable(event.keyAt(i), event.valueAt(i));
            }
            for (int i = 0; i < data.size(); i++) {
                binding.setVariable(data.keyAt(i), data.valueAt(i));
            }
            binding.executePendingBindings();
        }
    }
}
