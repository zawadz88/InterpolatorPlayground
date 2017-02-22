package com.example.android.interpolatorplayground.custom;

import android.view.animation.Interpolator;

/**
 * EaseInElastic as in:
 * https://github.com/PrimaryFeather/Sparrow-Framework/blob/9de0e1f03c9d3fc532c4f7d6f60114387adb786e/sparrow/src/Classes/SPTransitions.m#L86
 *
 * @author Piotr Zawadzki
 */
public class EaseInElasticInterpolator implements Interpolator {

    private static final float MAGIC_CONSTANT_P = 0.3f;

    private static final float MAGIC_CONSTANT_S = MAGIC_CONSTANT_P / 4.0f;

    private static final double TWO_PI = 2.0f * Math.PI;

    @Override
    public float getInterpolation(float input) {
        return calculateInterpolation(input);
    }

    public static float calculateInterpolation(float ratio) {
        if (ratio == 0.0f || ratio == 1.0f) {
            return ratio;
        } else {
            float inverseRatio = ratio - 1.0f;
            return (float) (-1.0f * Math.pow(2.0, 10.0 * inverseRatio) * Math.sin((inverseRatio - MAGIC_CONSTANT_S) * TWO_PI / MAGIC_CONSTANT_P));
        }

    }
}
