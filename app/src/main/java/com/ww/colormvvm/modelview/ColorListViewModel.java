package com.ww.colormvvm.modelview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ww.colormvvm.ColorApp;
import com.ww.colormvvm.db.entity.ColorEntity;
import com.ww.colormvvm.model.Color;

import java.util.List;

/**
 * Created by wangwang on 2018/3/22.
 */

public class ColorListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<ColorEntity>> allColors;
    private final MediatorLiveData<ColorEntity> selectedColor;
    public LiveData<ColorEntity> getSelectedColor() {
        return selectedColor;
    }
    public void changeEndColor(ColorEntity colorEntity){
        selectedColor.setValue(colorEntity);
    }
    public ColorListViewModel(@NonNull Application application) {
        super(application);
        selectedColor=new MediatorLiveData<>();
        allColors = new MediatorLiveData<>();
        allColors.setValue(null);
        LiveData<List<ColorEntity>> colors = ((ColorApp) application).getRepository()
                .getColors();
        LiveData<ColorEntity> color = ((ColorApp) application).getRepository()
                .getFirstColor();
        allColors.addSource(colors, allColors::setValue);
        selectedColor.addSource(color,selectedColor::setValue);
    }
    public LiveData<List<ColorEntity>> getColors() {
        return allColors;
    }
}
