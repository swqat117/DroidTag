package com.quascenta.petersroad.droidtag.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.widgets.CircularProgress.CircleProgressView;
import com.quascenta.petersroad.droidtag.widgets.CircularProgress.Direction;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 2/23/2017.
 */

public class StatusWidget extends LinearLayout {

    @Bind(R.id.tv_status_state)
    TextView status;

    CircleProgressView mCircleView;

    public StatusWidget(Context context) {
        super(context);
        init();
    }

    public StatusWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatusWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.progress_widget, this);
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        ButterKnife.bind(this);
    }


    public void setStatus(int max, int value) {
        if (max < 10) {
            mCircleView.setBlockCount(max);
            mCircleView.setBlockScale(1);
            mCircleView.setRoundToBlock(true);
        }
        mCircleView.setMaxValue(max);
        mCircleView.setValueAnimated(0, value, 1000);
        mCircleView.setDirection(Direction.CW);
        mCircleView.setText(String.format("%d", value));


    }

    public void setText(String text) {
        status.setText(text);
    }

    public void setColor(int color) {
        status.setTextColor(color);
        mCircleView.setTextColor(color);
        mCircleView.setBarColor(color);

    }


}
