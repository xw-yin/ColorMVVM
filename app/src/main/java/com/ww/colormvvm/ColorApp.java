package com.ww.colormvvm;

import android.app.Application;

import com.ww.colormvvm.db.ColorDatabase;

/**
 * Created by wangwang on 2018/3/22.
 */

public class ColorApp extends Application {
    private ColorExecutors colorExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        colorExecutors=new ColorExecutors();
    }
    public ColorDatabase getDatabase(){
        return ColorDatabase.getInstance(this,colorExecutors);
    }
    public ColorRepository getRepository() {
        return ColorRepository.getInstance(getDatabase());
    }
}
