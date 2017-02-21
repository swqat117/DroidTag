package com.quascenta.petersroad.droidtag.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;

/**
 * Created by AKSHAY on 2/21/2017.
 */

public class CustomCompoundView extends RelativeLayout {

    LayoutInflater mInflater;
    TextView textView;
    ImageView imageView;

    Paint p;

    public CustomCompoundView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();

    }

    public CustomCompoundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public CustomCompoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        p.setColor(Color.BLACK);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        // set the dimensions
        if (widthWithoutPadding > heigthWithoutPadding) {
            size = heigthWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    public void init() {
        p = new Paint();
        View v = mInflater.inflate(R.layout.lineview, this, true);
        imageView = (ImageView) v.findViewById(R.id.iv2_icon_alert);
        textView = (TextView) v.findViewById(R.id.status_state);

    }
}

