package com.ww.colormvvm.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ww.colormvvm.R;
import com.ww.colormvvm.databinding.ActivityMainBinding;
import com.ww.colormvvm.model.Color;
import com.ww.colormvvm.modelview.MainViewModel;

/**
 * Created by wangwang on 2018/3/22.
 */

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        if(savedInstanceState==null){
            ColorListFragment colorListFragment=new ColorListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,colorListFragment,ColorListFragment.TAG).commit();
        }
        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        subscribeUI(mainViewModel);
    }

    private void subscribeUI(MainViewModel mainViewModel) {
        mainViewModel.getEndColor().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer endColor) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ObjectAnimator objectAnimatorStatusBar=ObjectAnimator.ofInt(getWindow(),"statusBarColor",getWindow().getStatusBarColor(),endColor);
                    objectAnimatorStatusBar.setEvaluator(new ArgbEvaluator());
                    objectAnimatorStatusBar.setDuration(1000);
                    objectAnimatorStatusBar.start();
                }
                Drawable background=activityMainBinding.getRoot().getBackground();
                ObjectAnimator objectAnimatorbg=ObjectAnimator.ofInt(activityMainBinding.getRoot(),"backgroundColor",(background instanceof ColorDrawable?((ColorDrawable) background).getColor(): android.graphics.Color.TRANSPARENT),endColor);
                objectAnimatorbg.setEvaluator(new ArgbEvaluator());
                objectAnimatorbg.setDuration(1000);
                objectAnimatorbg.start();
            }
        });
    }
    public void changeBg(int endColor){
        mainViewModel.changeEndColor(endColor);
    }
    public void show(Color color) {

        ColorDetailFragment colorDetailFragment = ColorDetailFragment.forColor(color.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("color")
                .replace(R.id.fragment_container,
                        colorDetailFragment, null).commit();
    }
}
