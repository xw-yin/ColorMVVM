package com.ww.colormvvm.modelview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.ww.colormvvm.ColorApp;
import com.ww.colormvvm.ColorRepository;
import com.ww.colormvvm.db.entity.ColorEntity;

/**
 * Created by wangwang on 2018/3/25.
 */

public class ColorDetailViewModel extends AndroidViewModel {
    private final LiveData<ColorEntity> mObservableColor;
    public ObservableField<ColorEntity> color=new ObservableField<>();
    private final  String mColorId;
    public LiveData<ColorEntity> getObservableColor() {
        return mObservableColor;
    }
    public void setColor(ColorEntity color) {
        this.color.set(color);
    }
    public ColorDetailViewModel(@NonNull Application application, ColorRepository colorRepository, final String colorId){
        super(application);
        mColorId=colorId;
        mObservableColor=colorRepository.loadColor(mColorId);
    }
    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final String colorId;
        private final ColorRepository colorRepository;

        public Factory(@NonNull Application application, String colorId) {
            this.application = application;
            this.colorId = colorId;
            colorRepository=((ColorApp)application).getRepository();
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T)new ColorDetailViewModel(application,colorRepository,colorId);
        }
    }
}
