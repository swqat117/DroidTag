package com.quascenta.petersroad.droidtag.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.quascenta.petersroad.droidtag.R;

/**
 * Created by AKSHAY on 2/23/2017.
 */

public class DashboardView extends LinearLayout {

    StatusWidget devices_completed, devices_alerted, devices_pending, devices_total;

    public DashboardView(Context context) {
        super(context);
        init();
    }

    public DashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.profile_view, this);
        devices_completed = (StatusWidget) findViewById(R.id.dashboard_completed);
        devices_total = (StatusWidget) findViewById(R.id.dashboard_all);
        devices_alerted = (StatusWidget) findViewById(R.id.dashboard_alerted);
        devices_pending = (StatusWidget) findViewById(R.id.dashboard_pending);

    }

    public void setDevices_All(int x, int y) {
        devices_total.setStatus(x, y);
        devices_total.setText("All Loggers");

    }

    public void setDevices_completed(int x, int y) {
        devices_completed.setStatus(x, y);
        devices_completed.setColor(Color.parseColor("#00E676"));
        devices_completed.setText("Completed");

    }

    public void setDevices_alerted(int x, int y) {
        devices_alerted.setStatus(x, y);
        devices_alerted.setColor(Color.parseColor("#f44336"));
        devices_alerted.setText("  Alerted");

    }

    public void setDevices_pending(int x, int y) {
        devices_pending.setStatus(x, y);
        devices_pending.setColor(Color.parseColor("#FFC107"));
        devices_pending.setText("  Pending");

    }


}
