package com.ww.colormvvm.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ww.colormvvm.R;
import com.ww.colormvvm.databinding.ItemColorBinding;
import com.ww.colormvvm.model.Color;

import java.util.List;
import java.util.Objects;

/**
 * Created by wangwang on 2018/3/22.
 */

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.ColorViewHolder>{
    List<? extends Color> mColorList;
    @Nullable
    private final ColorClickCallback colorClickCallback;

    public ColorListAdapter(ColorClickCallback colorClickCallback) {
        this.colorClickCallback = colorClickCallback;
    }
    public void setmColorList(final List<? extends Color> colorList) {
        if (mColorList == null) {
            mColorList = colorList;
            notifyItemRangeInserted(0, colorList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mColorList.size();
                }

                @Override
                public int getNewListSize() {
                    return colorList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mColorList.get(oldItemPosition).getId() ==
                            colorList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Color newProduct = colorList.get(newItemPosition);
                    Color oldProduct = mColorList.get(oldItemPosition);
                    return newProduct.getId() == oldProduct.getId();
                }
            });
            mColorList = colorList;
            result.dispatchUpdatesTo(this);
        }
    }
    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemColorBinding itemColorBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_color,parent,false);
        itemColorBinding.setCallback(colorClickCallback);
        return new ColorViewHolder(itemColorBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.itemColorBinding.setColor(mColorList.get(position));
        holder.itemColorBinding.view.setBackgroundColor(android.graphics.Color.parseColor(mColorList.get(position).getHex()));
        holder.itemColorBinding.num.setTextColor(android.graphics.Color.parseColor(mColorList.get(position).getHex()));
        holder.itemColorBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mColorList==null ? 0 : mColorList.size();
    }

    static class ColorViewHolder extends RecyclerView.ViewHolder{
        final ItemColorBinding itemColorBinding;
        public ColorViewHolder(ItemColorBinding itemColorBinding) {
            super(itemColorBinding.getRoot());
            this.itemColorBinding = itemColorBinding;
        }
    }
}
