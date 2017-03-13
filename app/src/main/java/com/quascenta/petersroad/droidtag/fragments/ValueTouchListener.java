package com.quascenta.petersroad.droidtag.fragments;

import android.content.Context;
import android.widget.Toast;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PointValue;

/**
 * Created by AKSHAY on 3/2/2017.
 */

public class ValueTouchListener implements LineChartOnValueSelectListener {
    float x = 0;
    Context context;

    public ValueTouchListener(Context context) {
        this.context = context;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
        x = value.getY();

    }

    @Override
    public void onValueDeselected() {
        // TODO Auto-generated method stub
        Toast.makeText(context.getApplicationContext(), "Selected: " + x, Toast.LENGTH_SHORT).show();

    }

}

