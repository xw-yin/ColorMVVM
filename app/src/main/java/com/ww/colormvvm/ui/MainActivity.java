package com.ww.colormvvm.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
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

/**
 * Created by wangwang on 2018/3/22.
 */

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private int mStartColor= android.graphics.Color.parseColor("#FFFFFF");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        if(savedInstanceState==null){
            ColorListFragment colorListFragment=new ColorListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,colorListFragment,ColorListFragment.TAG).commit();
        }
    }
    public void changeBg(int endColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator objectAnimatorStatusBar=ObjectAnimator.ofInt(getWindow(),"statusBarColor",mStartColor,endColor);
            objectAnimatorStatusBar.setEvaluator(new ArgbEvaluator());
            objectAnimatorStatusBar.setDuration(1000);
            objectAnimatorStatusBar.start();
        }
        ObjectAnimator objectAnimatorbg=ObjectAnimator.ofInt(activityMainBinding.getRoot(),"backgroundColor",mStartColor,endColor);
        objectAnimatorbg.setEvaluator(new ArgbEvaluator());
        objectAnimatorbg.setDuration(1000);
        objectAnimatorbg.start();
        mStartColor=endColor;
    }


    public void show(Color color,int endColor) {

        ColorDetailFragment colorDetailFragment = ColorDetailFragment.forColor(color.getId(),endColor);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("color")
                .replace(R.id.fragment_container,
                        colorDetailFragment, null).commit();
    }
}
