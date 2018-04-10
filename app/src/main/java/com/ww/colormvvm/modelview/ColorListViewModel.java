package com.ww.colormvvm.modelview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.ww.colormvvm.ColorApp;
import com.ww.colormvvm.db.entity.ColorEntity;

import java.util.List;

/**
 * Created by wangwang on 2018/3/22.
 */

public class ColorListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<ColorEntity>> mObservableColors;
    public ColorListViewModel(@NonNull Application application) {
        super(application);
        mObservableColors = new MediatorLiveData<>();
        mObservableColors.setValue(null);
        LiveData<List<ColorEntity>> colors = ((ColorApp) application).getRepository()
                .getColors();
        mObservableColors.addSource(colors, mObservableColors::setValue);
    }
    public LiveData<List<ColorEntity>> getColors() {
        return mObservableColors;
    }
}
