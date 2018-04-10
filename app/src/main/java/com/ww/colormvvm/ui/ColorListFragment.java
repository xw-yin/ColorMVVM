package com.ww.colormvvm.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Lifecycle;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.ww.colormvvm.R;
import com.ww.colormvvm.databinding.FragmentColorlistBinding;
import com.ww.colormvvm.db.entity.ColorEntity;
import com.ww.colormvvm.model.Color;
import com.ww.colormvvm.modelview.ColorListViewModel;

import java.util.List;

/**
 * Created by wangwang on 2018/3/22.
 */

public class ColorListFragment extends Fragment{
    public static final String TAG = "ColorListViewModel";
    private ColorListAdapter colorListAdapter;
    private FragmentColorlistBinding fragmentColorlistBinding;
    private SnapHelper snapHelper;
    private Configuration configuration;
    private Color colorNow;
    private int endColor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentColorlistBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_colorlist,container,false);
        fragmentColorlistBinding.colorsList.setLayoutManager(new GridLayoutManager(getContext(),6));
        snapHelper=new GravitySnapHelper(Gravity.TOP);
        snapHelper.attachToRecyclerView(fragmentColorlistBinding.colorsList);
        colorListAdapter=new ColorListAdapter(colorClickCallback);
        fragmentColorlistBinding.colorsList.setAdapter(colorListAdapter);
        configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
           fragmentColorlistBinding.setVisible(true);
        }else  if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            fragmentColorlistBinding.setVisible(false);
        }
       return fragmentColorlistBinding.getRoot();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            colorNow= (Color) savedInstanceState.getSerializable("colorNow");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ColorListViewModel viewModel = ViewModelProviders.of(this).get(ColorListViewModel.class);
        subscribeUi(viewModel);
    }
    private void subscribeUi(ColorListViewModel viewModel) {
        viewModel.getColors().observe(this, new Observer<List<ColorEntity>>() {
            @Override
            public void onChanged(@Nullable List<ColorEntity> colorEntities) {
                if (colorEntities != null) {
                    fragmentColorlistBinding.setIsLoading(false);
                    colorListAdapter.setmColorList(colorEntities);
                    if(colorNow==null){
                        colorNow=colorEntities.get(0);
                    }
                    changeColor(colorNow);
                }else {
                    fragmentColorlistBinding.setIsLoading(true);
                }
                fragmentColorlistBinding.executePendingBindings();
            }
        });
    }

    private final ColorClickCallback colorClickCallback = new ColorClickCallback() {
        @Override
        public void onClick(Color color) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                colorNow=color;
                changeColor(colorNow);
           if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    ((MainActivity) getActivity()).show(color,endColor);
                }
            }
        }
    };
    private void changeColor(Color color){
        fragmentColorlistBinding.setColor(color);
        endColor = android.graphics.Color.parseColor(color.getHex());
        fragmentColorlistBinding.rNumber.setNumberSetOfInt(Integer.valueOf(color.getR())).startAnim();
        fragmentColorlistBinding.gNumber.setNumberSetOfInt(Integer.valueOf(color.getG())).startAnim();
        fragmentColorlistBinding.bNumber.setNumberSetOfInt(Integer.valueOf(color.getB())).startAnim();
        decreaseTextAlpha();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentColorlistBinding.colorNameJa.setText(color.getJpname());
                fragmentColorlistBinding.colorNameEn.setText(color.getEnname());
            }
        },500);
        increaseTextAlpha();
        ((MainActivity)getActivity()).changeBg(endColor);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("colorNow",colorNow);
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
