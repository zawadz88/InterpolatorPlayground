package com.example.android.interpolatorplayground.custom;

import android.view.animation.Interpolator;

/**
 * EaseInBounce as in:
 * https://github.com/PrimaryFeather/Sparrow-Framework/blob/9de0e1f03c9d3fc532c4f7d6f60114387adb786e/sparrow/src/Classes/SPTransitions.m#L121
 *
 * @author Piotr Zawadzki
 */
public class EaseInBounceInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        return calculateInterpolation(input);
    }

    public static float calculateInterpolation(float ratio) {
        return 1.0f - EaseOutBounceInterpolator.calculateInterpolation(1.0f - ratio);
    }
}
