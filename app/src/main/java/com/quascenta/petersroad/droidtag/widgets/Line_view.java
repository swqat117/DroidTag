package com.quascenta.petersroad.droidtag.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 2/22/2017.
 */

public class Line_view extends LinearLayout {

    public static final int STATUS_NOT_UPLOADED = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_ALERT = 2;


    @Bind(R.id.iv1_icon_start)
    ImageView start;
    @Bind(R.id.view1_horizontal_line)
    View lineview1;
    @Bind(R.id.iv2_icon_alert)
    ImageView status;
    @Bind(R.id.tv_status_state)
    TextView status_text_state;
    @Bind(R.id.view2_horizontal_line)
    View lineview2;
    @Bind(R.id.iv3_icon_alert)
    ImageView finish;


    public Line_view(Context context) {
        this(context, null);
    }

    public Line_view(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Line_view(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Line_view(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public boolean setState(int x) {

        switch (x) {

            case STATUS_SUCCESS:
                start.setImageResource(R.drawable.ic_place_green_24dp);
                status.setImageResource(R.drawable.ic_check_circle_black_24dp);
                finish.setImageResource(R.drawable.ic_right_green);
                lineview1.setBackgroundColor(Color.parseColor("#69F0AE"));
                lineview2.setBackgroundColor(Color.parseColor("#69F0AE"));
                status_text_state.setTextColor(Color.parseColor("#69F0AE"));
                status_text_state.setText(" Success");
                return true;
            case STATUS_ALERT:
                start.setImageResource(R.drawable.ic_place_black_24dp);
                status.setImageResource(R.drawable.ic_error_black_24dp);
                finish.setImageResource(R.drawable.ic_chevron_right_black_24dp);
                lineview1.setBackgroundColor(Color.parseColor("#f44336"));
                lineview2.setBackgroundColor(Color.parseColor("#f44336"));
                status_text_state.setTextColor(Color.parseColor("#f44336"));
                status_text_state.setText("Data Alert");
                return false;
            case STATUS_NOT_UPLOADED:
                start.setImageResource(R.drawable.ic_place_yellow);
                status.setImageResource(R.drawable.ic_error_yellow);
                finish.setImageResource(R.drawable.ic_yellow_right);
                lineview1.setBackgroundColor(Color.parseColor("#FFC107"));
                lineview2.setBackgroundColor(Color.parseColor("#FFC107"));
                status_text_state.setTextColor(Color.parseColor("#FFCC00"));
                status_text_state.setText("In Progress");
                return false;
            default:
                return false;

        }
    }


    private void init() {

        inflate(getContext(), R.layout.emerge, this);
        ButterKnife.bind(this);

    }


}