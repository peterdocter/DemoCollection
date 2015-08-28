package com.github.airk.democollection.bounce;

import android.view.animation.Interpolator;

/**
 * @author liukai(kevinliukai@diditaxi.com)
 * @since 15/8/7.
 */
public class LittleBounceInterpolator implements Interpolator {
    //2.5xx <0.63246
    //9.5(x-0.81675)(x-0.81675)+0.67778
    private float bounce(float t) {
        return t * t * 10;
    }

    @Override
    public float getInterpolation(float t) {
        if (t < 0.6742f)
            return bounce(t - 0.31623f);
        else
            return bounce(t - 0.81675f) + 0.6635f;
    }
}
