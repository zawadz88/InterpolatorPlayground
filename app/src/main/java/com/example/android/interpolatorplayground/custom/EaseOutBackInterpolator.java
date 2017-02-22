package com.example.android.interpolatorplayground.custom;

import android.view.animation.Interpolator;

/**
 * EaseOutBack as in:
 * https://github.com/PrimaryFeather/Sparrow-Framework/blob/9de0e1f03c9d3fc532c4f7d6f60114387adb786e/sparrow/src/Classes/SPTransitions.m#L67
 *
 * @author Piotr Zawadzki
 */
public class EaseOutBackInterpolator implements Interpolator {

    private static final float MAGIC_CONSTANT = 1.70158f;

    @Override
    public float getInterpolation(float input) {
        return calculateInterpolation(input);
    }

    public static float calculateInterpolation(float input) {
        float inverseRatio = input - 1.0f;
        return (float) (Math.pow(inverseRatio, 2.0) * ((MAGIC_CONSTANT + 1.0f) * inverseRatio + MAGIC_CONSTANT) + 1.0f);
    }
}
