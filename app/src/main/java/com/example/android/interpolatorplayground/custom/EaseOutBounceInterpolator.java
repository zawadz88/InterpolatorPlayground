package com.example.android.interpolatorplayground.custom;

import android.view.animation.Interpolator;

/**
 * EaseOutBounce as in:
 * https://github.com/PrimaryFeather/Sparrow-Framework/blob/9de0e1f03c9d3fc532c4f7d6f60114387adb786e/sparrow/src/Classes/SPTransitions.m#L126
 *
 * @author Piotr Zawadzki
 */
public class EaseOutBounceInterpolator implements Interpolator {

    private static final float MAGIC_CONSTANT_S = 7.5625f;

    private static final float MAGIC_CONSTANT_P = 2.75f;

    @Override
    public float getInterpolation(float input) {
        return calculateInterpolation(input);
    }

    public static float calculateInterpolation(float ratio) {
        if (ratio < (1.0f / MAGIC_CONSTANT_P)) {
            return (float) (MAGIC_CONSTANT_S * Math.pow(ratio, 2.0f));
        } else if (ratio < (2.0f / MAGIC_CONSTANT_P)) {
            return (float) (MAGIC_CONSTANT_S * Math.pow(ratio - (1.5f / MAGIC_CONSTANT_P), 2.0) + 0.75f);
        } else if (ratio < (2.5f / MAGIC_CONSTANT_P)) {
            return (float) (MAGIC_CONSTANT_S * Math.pow(ratio - (2.25f / MAGIC_CONSTANT_P), 2.0f) + 0.9375f);
        } else {
            return (float) (MAGIC_CONSTANT_S * Math.pow(ratio - 2.625f / MAGIC_CONSTANT_P, 2.0) + 0.984375f);
        }
    }
}
