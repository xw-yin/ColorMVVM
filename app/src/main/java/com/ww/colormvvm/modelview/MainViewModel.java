package com.ww.colormvvm.modelview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class MainViewModel extends AndroidViewModel {
    private  MutableLiveData<Integer> mEndColor;

    public MutableLiveData<Integer> getEndColor() {
        return mEndColor;
    }

    public void changeEndColor(Integer endColor) {
        this.mEndColor.setValue(endColor);
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        mEndColor=new MutableLiveData<>();
    }
}
