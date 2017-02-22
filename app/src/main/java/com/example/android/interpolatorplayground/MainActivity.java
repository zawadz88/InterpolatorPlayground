/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.interpolatorplayground;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.interpolatorplayground.custom.EaseInBackInterpolator;
import com.example.android.interpolatorplayground.custom.EaseInBounceInterpolator;
import com.example.android.interpolatorplayground.custom.EaseInElasticInterpolator;
import com.example.android.interpolatorplayground.custom.EaseInInterpolator;
import com.example.android.interpolatorplayground.custom.EaseInOutBackInterpolator;
import com.example.android.interpolatorplayground.custom.EaseInOutBounceInterpolator;
import com.example.android.interpolatorplayground.custom.EaseInOutElasticInterpolator;
import com.example.android.interpolatorplayground.custom.EaseInOutInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutBackInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutBounceInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutElasticInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutInBackInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutInBounceInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutInElasticInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutInInterpolator;
import com.example.android.interpolatorplayground.custom.EaseOutInterpolator;

import java.lang.reflect.Constructor;

import static android.widget.LinearLayout.LayoutParams;

/**
 * This activity allows the user to visualize the timing curves for
 * most of the standard Interpolator objects. It allows parameterized
 * interpolators to be changed, including manipulating the control
 * points of PathInterpolator to create custom curves.
 *
 * An extended list of interpolators was made based on:
 * <ul>
 *     <li>http://stackoverflow.com/questions/5161465/how-to-create-custom-easing-function-with-core-animation</li>
 *     <li>http://wiki.sparrow-framework.org/_media/manual/transitions.png</li>
 *     <li>https://github.com/PrimaryFeather/Sparrow-Framework/blob/master/sparrow/src/Classes/SPTransitions.m</li>
 * </ul>
 */
public class MainActivity extends AppCompatActivity {

    CurveVisualizer mVisualizer;
    TimingVisualizer mTimingVisualizer;
    ObjectAnimator mAnimator = null;
    long mDuration = 300;
    private int mDefaultMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mDefaultMargin = (int) (8 * metrics.density);

        final LinearLayout paramatersParent = (LinearLayout) findViewById(R.id.linearLayout);
        final LinearLayout gridParent = (LinearLayout) findViewById(R.id.linearLayout2);
        final SeekBar durationSeeker = (SeekBar) findViewById(R.id.durationSeeker);
        final TextView durationLabel = (TextView) findViewById(R.id.durationLabel);
        mTimingVisualizer = (TimingVisualizer) findViewById(R.id.timingVisualizer);
        mAnimator = ObjectAnimator.ofFloat(this, "fraction", 0, 1);

        mVisualizer = new CurveVisualizer(this);
        gridParent.addView(mVisualizer);

        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                populateParametersUI(adapterView.getItemAtPosition(pos).toString(),
                        paramatersParent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        durationSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                durationLabel.setText("Duration " + progress + "ms");
                mDuration = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * Called when the "Run" button is clicked. It cancels any running animation
     * and starts a new one with the values specified in the UI.
     */
    public void runAnimation(View view) {
        mAnimator.cancel();
        mAnimator.setInterpolator(mVisualizer.getInterpolator());
        mAnimator.setDuration(mDuration);
        mAnimator.start();
    }

    /**
     * This method is called to populate the UI according to which interpolator was
     * selected.
     */
    private void populateParametersUI(String interpolatorName, LinearLayout parent) {
        parent.removeAllViews();
        try {
            switch (interpolatorName) {
                case "AccelerateDecelerate":
                    mVisualizer.setInterpolator(new AccelerateDecelerateInterpolator());
                    break;
                case "Linear":
                    mVisualizer.setInterpolator(new LinearInterpolator());
                    break;
                case "Bounce":
                    mVisualizer.setInterpolator(new BounceInterpolator());
                    break;
                case "Accelerate":
                    Constructor<AccelerateInterpolator> decelConstructor =
                            AccelerateInterpolator.class.getConstructor(float.class);
                    createParamaterizedInterpolator(parent, decelConstructor, "Factor", 1, 5, 1);
                    break;
                case "Decelerate":
                    Constructor<DecelerateInterpolator> accelConstructor =
                            DecelerateInterpolator.class.getConstructor(float.class);
                    createParamaterizedInterpolator(parent, accelConstructor, "Factor", 1, 5, 1);
                    break;
                case "Overshoot":
                    Constructor<OvershootInterpolator> overshootConstructor =
                            OvershootInterpolator.class.getConstructor(float.class);
                    createParamaterizedInterpolator(parent, overshootConstructor, "Tension", 1, 5, 1);
                    break;
                case "Anticipate":
                    Constructor<AnticipateInterpolator> anticipateConstructor =
                            AnticipateInterpolator.class.getConstructor(float.class);
                    createParamaterizedInterpolator(parent, anticipateConstructor, "Tension", 1, 5, 1);
                    break;
                case "FastOutSlowIn":
                    mVisualizer.setInterpolator(new FastOutSlowInInterpolator());
                    break;
                case "LinearOutSlowIn":
                    mVisualizer.setInterpolator(new LinearOutSlowInInterpolator());
                    break;
                case "FastOutLinearIn":
                    mVisualizer.setInterpolator(new FastOutLinearInInterpolator());
                    break;
                case "EaseIn":
                    mVisualizer.setInterpolator(new EaseInInterpolator());
                    break;
                case "EaseOut":
                    mVisualizer.setInterpolator(new EaseOutInterpolator());
                    break;
                case "EaseInOut":
                    mVisualizer.setInterpolator(new EaseInOutInterpolator());
                    break;
                case "EaseOutIn":
                    mVisualizer.setInterpolator(new EaseOutInInterpolator());
                    break;
                case "EaseInBack":
                    mVisualizer.setInterpolator(new EaseInBackInterpolator());
                    break;
                case "EaseOutBack":
                    mVisualizer.setInterpolator(new EaseOutBackInterpolator());
                    break;
                case "EaseInOutBack":
                    mVisualizer.setInterpolator(new EaseInOutBackInterpolator());
                    break;
                case "EaseOutInBack":
                    mVisualizer.setInterpolator(new EaseOutInBackInterpolator());
                    break;
                case "EaseInBounce":
                    mVisualizer.setInterpolator(new EaseInBounceInterpolator());
                    break;
                case "EaseOutBounce":
                    mVisualizer.setInterpolator(new EaseOutBounceInterpolator());
                    break;
                case "EaseInOutBounce":
                    mVisualizer.setInterpolator(new EaseInOutBounceInterpolator());
                    break;
                case "EaseOutInBounce":
                    mVisualizer.setInterpolator(new EaseOutInBounceInterpolator());
                    break;
                case "EaseInElastic":
                    mVisualizer.setInterpolator(new EaseInElasticInterpolator());
                    break;
                case "EaseOutElastic":
                    mVisualizer.setInterpolator(new EaseOutElasticInterpolator());
                    break;
                case "EaseInOutElastic":
                    mVisualizer.setInterpolator(new EaseInOutElasticInterpolator());
                    break;
                case "EaseOutInElastic":
                    mVisualizer.setInterpolator(new EaseOutInElasticInterpolator());
                    break;
            }
        } catch (NoSuchMethodException e) {
            Log.e("InterpolatorPlayground", "Error constructing interpolator: " + e);
        }
    }

    /**
     * Creates an Interpolator that takes a single parameter in its constructor.
     * The min/max/default parameters determine how the interpolator is initially
     * set up as well as the values used in the SeekBar for changing this value.
     */
    private void createParamaterizedInterpolator(LinearLayout parent,
        final Constructor constructor, final String name,
        final float min, final float max, final float defaultValue) {
        LinearLayout inputContainer = new LinearLayout(this);
        inputContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(mDefaultMargin, mDefaultMargin, mDefaultMargin, mDefaultMargin);
        inputContainer.setLayoutParams(params);

        final TextView label = new TextView(this);
        params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = .5f;
        label.setLayoutParams(params);
        String formattedValue = String.format(" %.2f", defaultValue);
        label.setText(name + formattedValue);

        final SeekBar seek = new SeekBar(this);
        params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = .5f;
        seek.setLayoutParams(params);
        seek.setMax(100);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float percentage = (float) i / 100;
                float value = min + percentage * (max - min);
                String formattedValue = String.format(" %.2f", value);
                try {
                    mVisualizer.setInterpolator((Interpolator) constructor.newInstance(value));
                } catch (Throwable error) {
                    Log.e("interpolatorPlayground", error.getMessage());
                }
                label.setText(name + formattedValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        inputContainer.addView(label);
        inputContainer.addView(seek);
        parent.addView(inputContainer);

        try {
            mVisualizer.setInterpolator((Interpolator) constructor.newInstance(defaultValue));
        } catch (Throwable error) {
            Log.e("interpolatorPlayground", error.getMessage());
        }
    }

    /**
     * This method is called by the animation to update the position of the animated
     * objects in the curve view as well as the view at the bottom showing sample animations.
     */
    @SuppressWarnings("unused")
    public void setFraction(float fraction) {
        mTimingVisualizer.setFraction(fraction);
        mVisualizer.setFraction(fraction);
    }

}
