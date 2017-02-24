package com.quascenta.petersroad.droidtag.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.quascenta.petersroad.droidtag.EventListeners.HeaderOnClickListener;
import com.quascenta.petersroad.droidtag.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 2/2/2017.
 */

public class HeaderView extends LinearLayout {
    @Bind(R.id.tv_device_header_timestamp)
    ImageView timestamp;
    @Bind(R.id.tv_device_header_Temp_sensor)
    ImageView tempSensor;
    @Bind(R.id.tv_device_header_Rh_sensor)
    ImageView RhSensor;
    HeaderOnClickListener headerOnClickListener;
    private Context context;

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews(context);
    }

    public HeaderView(Context context) {
        super(context);
        this.context = context;
        // this.headerOnClickListener = headerOnClickListener;
        LayoutInflater.from(context).inflate(R.layout.view_header, this);
        initViews(context);

    }

    @OnClick(R.id.tv_device_header_timestamp)
    public void onClick_Timestamp() {
        Log.d("onclick timestamp", "Passed");
    }

    @OnClick(R.id.tv_device_header_Temp_sensor)
    public void onClick_temp() {
        Log.d(" onClick_temp", "Passed");
    }

    @OnClick(R.id.tv_device_header_Rh_sensor)
    public void onClick_RH() {
        Log.d(" onClick_RH", "Passed");
    }


    public void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_header, this);
        ButterKnife.bind(this);

        Picasso.with(context).load(R.drawable.humidity).resize(90, 80).into(tempSensor);
        Picasso.with(context).load(R.drawable.temp).resize(90, 80).into(RhSensor);

    }


    public void setImage() {
        Picasso.with(context).load(R.drawable.ic_timer_black_24dp).centerCrop().into(timestamp);


    }


}
