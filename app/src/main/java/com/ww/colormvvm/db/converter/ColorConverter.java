package com.ww.colormvvm.db.converter;

import android.databinding.BindingConversion;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class ColorConverter {
    @BindingConversion
    public static ColorDrawable convertColorStringToDrawable(String colorString) {
        int colorInt= Color.parseColor(colorString);
        return new ColorDrawable(colorInt);
    }
}
