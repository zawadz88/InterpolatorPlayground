package com.example.android.interpolatorplayground.custom;

import android.view.animation.Interpolator;

/**
 * EaseIn as in:
 * https://github.com/PrimaryFeather/Sparrow-Framework/blob/9de0e1f03c9d3fc532c4f7d6f60114387adb786e/sparrow/src/Classes/SPTransitions.m#L38
 *
 * @author Piotr Zawadzki
 */
public class EaseInInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        return calculateInterpolation(input);
    }

    public static float calculateInterpolation(float input) {
        return input * input * input;
    }
}
