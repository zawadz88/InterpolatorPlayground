package com.example.android.interpolatorplayground.custom;

import android.view.animation.Interpolator;

/**
 * EaseOutIn as in:
 * https://github.com/PrimaryFeather/Sparrow-Framework/blob/9de0e1f03c9d3fc532c4f7d6f60114387adb786e/sparrow/src/Classes/SPTransitions.m#L55
 *
 * @author Piotr Zawadzki
 */
public class EaseOutInInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        if (input < 0.5f) {
            return 0.5f * EaseOutInterpolator.calculateInterpolation(input * 2.0f);
        } else {
            return 0.5f * EaseInInterpolator.calculateInterpolation((input - 0.5f) * 2.0f) + 0.5f;
        }
    }
}
