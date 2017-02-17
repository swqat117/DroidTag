package com.quascenta.petersroad.droidtag;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 2/15/2017.
 */

public class MultiCheckDeviceViewHolder extends CheckableChildViewHolder {

    View mview;
    Context context;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @Bind(R.id.iv_icon_alert)
    ImageView iv_status;
    @Bind(R.id.iv2_icon_alert)
    ImageView iv2_status;
    @Bind(R.id.source_company_name)
    TextView mSource_company_name;
    @Bind(R.id.source_location)
    TextView mSource_location;
    @Bind(R.id.destination_company_name)
    TextView mDestination_company_name;
    @Bind(R.id.destination_location)
    TextView mDestination_location;
    @Bind(R.id.customer_tracking_id)
    TextView customer_tracking_id;
    @Bind(R.id.status_state)
    TextView status_state;
    @Bind(R.id.ib_options)
    CheckBox checkBox;


    public MultiCheckDeviceViewHolder(View itemView) {
        super(itemView);
        mview = itemView;
        context = mview.getContext();
        ButterKnife.bind(this, itemView);


    }

    @Override
    public Checkable getCheckable() {
        mview.setBackgroundColor(Color.parseColor("#33ee44"));
        return checkBox;

    }

    public boolean getCheckBox() {
        return checkBox.isChecked();
    }

    public void setCheckBox(boolean x) {
        this.checkBox.setChecked(x);
    }

    public String getCustomer_tracking_id() {
        return customer_tracking_id.getText().toString();
    }

    public void setCustomer_tracking_id(String customer_tracking_id) {
        this.customer_tracking_id.setText(customer_tracking_id);
    }

    public void getIv2_status(int x) {

        if (x == 0) {
            Picasso.with(context).load(R.drawable.message_alert).resize(30, 30).into(iv2_status);
        } else if (x == 1) {
            Picasso.with(context).load(R.drawable.message_alert).resize(30, 30).into(iv2_status);
        } else if (x == 2) {
            Picasso.with(context).load(R.drawable.ic_alarm_on_black_24dp).resize(30, 30).into(iv2_status);
        }

    }


    public void getIv_status(int x) {

        if (x == 0) {
            Picasso.with(context).load(R.drawable.ic_error_black_24dp).resize(60, 60).into(iv_status);
        } else if (x == 1) {
            Picasso.with(context).load(R.drawable.ic_error_black_24dp).resize(60, 60).into(iv_status);
        } else if (x == 2) {
            Picasso.with(context).load(R.drawable.ic_check_circle_black_24dp).resize(60, 60).into(iv_status);
        }

    }

    public String getmDestination_company_name() {
        return mDestination_company_name.getText().toString();
    }

    public void setmDestination_company_name(String destination_company_name) {
        this.mDestination_company_name.setText(destination_company_name);
    }


    public String getmDestination_location() {
        return mDestination_location.getText().toString();
    }

    public void setmDestination_location(String x) {
        this.mDestination_location.setText(x);
    }

    public String getmSource_company_name() {
        return mSource_company_name.getText().toString();
    }

    public void setmSource_company_name(String mSource_company_name) {
        this.mSource_company_name.setText(mSource_company_name);
    }

    public String getmSource_location() {
        return mSource_location.getText().toString();
    }

    public void setmSource_location(String mSource_location) {
        this.mSource_location.setText(mSource_location);
    }

    public View getMview() {
        return mview;
    }

    public void setMview(View mview) {
        this.mview = mview;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    public String getStatus_state() {
        return status_state.getText().toString();
    }

    public void setStatus_state(int x) {
        switch (x) {
            case 0:

                this.status_state.setText("Data not Uploaded");
                break;
            case 1:
                this.status_state.setText("Data Alert");
                break;
            case 2:
                this.status_state.setTextColor(Color.parseColor("#33ee44"));
                this.status_state.setText("Stable");
        }
    }

}

