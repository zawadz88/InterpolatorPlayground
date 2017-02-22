package com.example.android.interpolatorplayground.custom;

import android.view.animation.Interpolator;

/**
 * EaseOut as in:
 * https://github.com/PrimaryFeather/Sparrow-Framework/blob/9de0e1f03c9d3fc532c4f7d6f60114387adb786e/sparrow/src/Classes/SPTransitions.m#L43
 *
 * @author Piotr Zawadzki
 */
public class EaseOutInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        return calculateInterpolation(input);
    }

    public static float calculateInterpolation(float input) {
        float inverseRatio = input - 1.0f;
        return inverseRatio * inverseRatio * inverseRatio + 1.0f;
    }
}
