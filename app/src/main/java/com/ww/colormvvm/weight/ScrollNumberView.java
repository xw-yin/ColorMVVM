package com.ww.colormvvm.weight;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import com.ww.colormvvm.R;

/**
 * Created by gjz on 11/25/15.
 */
public class ScrollNumberView extends android.support.v7.widget.AppCompatTextView {

    private float currentNumberOfFloat = 0;
    private int currentNumberOfInt = 0;
    private int intNum;
    private float floatNum;
    private ObjectAnimator objectAnimator = new ObjectAnimator();
    private long duration = 0;
    private int delay = 0;
    private String format;
    private Interpolator interpolator = null;

    public ScrollNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollNumberView);
        intNum = typedArray.getInt(R.styleable.ScrollNumberView_intNum, 0);
        floatNum = typedArray.getFloat(R.styleable.ScrollNumberView_floatNum, 0);
        delay = typedArray.getInt(R.styleable.ScrollNumberView_delay, 0);
        duration=typedArray.getInt(R.styleable.ScrollNumberView_duration,1000);
        typedArray.recycle();
    }

    public void setIntNum(int intNum) {
        this.intNum = intNum;
        setNumberSetOfInt(intNum).startAnim();
    }

    public ScrollNumberView setNumberSetOfInt(int... numbers) {
        if (numbers.length == 1) {
            numbers = new int[] {this.currentNumberOfInt, numbers[0]};
        }
        this.objectAnimator = ObjectAnimator.ofInt(this, "currentNumberOfInt", numbers);

        return this;
    }


    public ScrollNumberView setNumberSetOfFloat(float... numbers) {
        if (numbers.length == 1) {
            numbers = new float[] {this.currentNumberOfFloat, numbers[0]};
        }
        this.objectAnimator = ObjectAnimator.ofFloat(this, "currentNumberOfFloat", numbers);

        return this;
    }

    public void setCurrentNumberOfInt(int number) {
        this.currentNumberOfInt = number;
        if (this.format == null) {
            this.setText(number + "");
        }else {
            this.setText(String.format(format, number));
        }
    }

    public void setCurrentNumberOfFloat(float number) {
        this.currentNumberOfFloat = number;
        if (this.format == null) {
            this.setText(number + "");
        }else {
            this.setText(String.format(format, number));
        }
    }


    public ScrollNumberView setDuration(long duration) {
        this.duration = duration;
        return this;
    }


    public ScrollNumberView setDelay(int delay) {
        this.delay = delay;
        return this;
    }


    public int getDelay() {
        return delay;
    }


    public ScrollNumberView setFormat(String format) {
        this.format = format;
        return this;
    }


    public String getFormat() {
        return format;
    }


    public ScrollNumberView setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }


    public Interpolator getInterpolator() {
        return interpolator;
    }


    public boolean isAnimRunning() {
        return objectAnimator.isRunning();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isAnimPaused() {
        return objectAnimator.isPaused();
    }


    public boolean isAnimStarted() {
        return objectAnimator.isStarted();
    }


    public void startAnim() {
        objectAnimator.setDuration(this.duration).setStartDelay(this.delay);
        objectAnimator.setInterpolator(this.interpolator);
        objectAnimator.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pauseAnim() {
        objectAnimator.pause();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeAnim() {
        objectAnimator.resume();
    }


}
