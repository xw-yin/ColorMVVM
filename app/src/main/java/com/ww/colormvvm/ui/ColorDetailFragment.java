package com.ww.colormvvm.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ww.colormvvm.ColorApp;
import com.ww.colormvvm.R;
import com.ww.colormvvm.databinding.FragmentColordetailBinding;
import com.ww.colormvvm.db.entity.ColorEntity;
import com.ww.colormvvm.model.Color;
import com.ww.colormvvm.modelview.ColorDetailViewModel;

/**
 * Created by wangwang on 2018/3/25.
 */

public class ColorDetailFragment extends Fragment {
    private FragmentColordetailBinding fragmentColordetailBinding;
    private static final String KEY_COLOR_ID = "color_id";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentColordetailBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_colordetail,container,false);
        return fragmentColordetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ColorDetailViewModel.Factory factory=new ColorDetailViewModel.Factory(
                getActivity().getApplication(),getArguments().getString(KEY_COLOR_ID)
        );
        final ColorDetailViewModel model= ViewModelProviders.of(this,factory).get(ColorDetailViewModel.class);
        subscribeToModel(model);
    }
private void subscribeToModel(final ColorDetailViewModel colorDetailViewModel){
        colorDetailViewModel.getObservableColor().observe(this, new Observer<ColorEntity>() {
            @Override
            public void onChanged(@Nullable ColorEntity colorEntity) {
                if(colorEntity!=null) {
                    colorDetailViewModel.setColor(colorEntity);
                    fragmentColordetailBinding.setColor(colorEntity);
                }
            }
        });
}
    public static ColorDetailFragment forColor(String colorId) {
        ColorDetailFragment fragment = new ColorDetailFragment();
        Bundle args = new Bundle();
        args.putString(KEY_COLOR_ID, colorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
