package com.ww.colormvvm.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ww.colormvvm.R;
import com.ww.colormvvm.databinding.FragmentColorlistBinding;
import com.ww.colormvvm.db.entity.ColorEntity;
import com.ww.colormvvm.model.Color;
import com.ww.colormvvm.modelview.ColorDetailViewModel;
import com.ww.colormvvm.modelview.ColorListViewModel;
import com.ww.colormvvm.modelview.MainViewModel;

import java.util.List;

/**
 * Created by wangwang on 2018/3/22.
 */

public class ColorListFragment extends Fragment{
    public static final String TAG = "ColorListViewModel";
    private ColorListAdapter colorListAdapter;
    private FragmentColorlistBinding fragmentColorlistBinding;
    private Configuration configuration;
    private ColorListViewModel viewModel;
    private GridLayoutManager gridLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentColorlistBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_colorlist,container,false);
        gridLayoutManager=new GridLayoutManager(getContext(),6);
        fragmentColorlistBinding.colorsList.setLayoutManager(gridLayoutManager);
        colorListAdapter=new ColorListAdapter(colorClickCallback);
        fragmentColorlistBinding.colorsList.setAdapter(colorListAdapter);
        configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            gridLayoutManager.setSpanCount(7);
            fragmentColorlistBinding.setVisible(true);
            Window window=getActivity().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else  if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager.setSpanCount(6);
            fragmentColorlistBinding.setVisible(false);
            Window window=getActivity().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

       return fragmentColorlistBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ColorListViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(ColorListViewModel viewModel) {
        viewModel.getColors().observe(this, new Observer<List<ColorEntity>>() {
            @Override
            public void onChanged(@Nullable List<ColorEntity> colorEntities) {
                if (colorEntities != null) {
                    fragmentColorlistBinding.setIsLoading(false);
                    colorListAdapter.setmColorList(colorEntities);
                }else {
                    fragmentColorlistBinding.setIsLoading(true);
                }
                fragmentColorlistBinding.executePendingBindings();
            }
        });
        viewModel.getSelectedColor().observe(this, new Observer<ColorEntity>() {
            @Override
            public void onChanged(@Nullable ColorEntity colorEntity) {
                if(colorEntity!=null) {
                    changeColor(colorEntity);
                }
            }
        });
    }

    private final ColorClickCallback colorClickCallback = new ColorClickCallback() {
        @Override
        public void onClick(ColorEntity colorEntity) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                viewModel.changeEndColor(colorEntity);
           if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    ((MainActivity) getActivity()).show(colorEntity);
                }
            }
        }
    };
    private void changeColor(ColorEntity colorEntity){
        fragmentColorlistBinding.setColor(colorEntity);
        decreaseTextAlpha();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentColorlistBinding.colorNameJa.setText(colorEntity.getJpname());
                fragmentColorlistBinding.colorNameEn.setText(colorEntity.getEnname());
            }
        },500);
        increaseTextAlpha();
        ((MainActivity)getActivity()).changeBg(android.graphics.Color.parseColor(colorEntity.getHex()));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void increaseTextAlpha(){
        ObjectAnimator objectAnimatorja=ObjectAnimator.ofFloat( fragmentColorlistBinding.colorNameJa,"alpha",0f,1f);
        objectAnimatorja.setDuration(500);
        objectAnimatorja.setStartDelay(500);
        objectAnimatorja.start();
        ObjectAnimator objectAnimatoren=ObjectAnimator.ofFloat( fragmentColorlistBinding.colorNameEn,"alpha",0f,1f);
        objectAnimatoren.setDuration(500);
        objectAnimatoren.setStartDelay(500);
        objectAnimatoren.start();
    }
    private void decreaseTextAlpha(){
        ObjectAnimator objectAnimatorja=ObjectAnimator.ofFloat( fragmentColorlistBinding.colorNameJa,"alpha",1f,0f);
        objectAnimatorja.setDuration(500);
        objectAnimatorja.start();
        ObjectAnimator objectAnimatoren=ObjectAnimator.ofFloat( fragmentColorlistBinding.colorNameEn,"alpha",1f,0f);
        objectAnimatoren.setDuration(500);
        objectAnimatoren.start();
    }
}
