package com.ww.colormvvm.ui;

import android.view.View;

import com.ww.colormvvm.db.entity.ColorEntity;

/**
 * Created by wangwang on 2018/3/22.
 */

public interface ColorClickCallback {
    void onClick(ColorEntity colorEntity);
}
