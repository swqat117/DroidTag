package com.quascenta.petersroad.droidtag.adapters;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.widgets.Line_view;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 2/15/2017.
 */

public class MultiCheckDeviceViewHolder1 extends CheckableChildViewHolder {

    View mview;
    Context context;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @Bind(R.id.iv_icon_alert)
    ImageView iv_status;


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

    @Bind(R.id.ib_options)
    CheckBox checkBox;
    Line_view line_view;

    public MultiCheckDeviceViewHolder1(View itemView) {
        super(itemView);
        mview = itemView;
        context = mview.getContext();
        line_view = (Line_view) mview.findViewById(R.id.line4);
        ButterKnife.bind(this, itemView);


    }

    @Override
    public Checkable getCheckable() {

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

    public void getState(int x) {

        if (x == 0) {
            iv_status.setImageResource(R.drawable.message_alert);
            line_view.setState(x);
        } else if (x == 2) {
            iv_status.setImageResource(R.drawable.ic_error_black_24dp);
            line_view.setState(x);
        } else if (x == 1) {
            iv_status.setImageResource(R.drawable.ic_check_circle_black_24dp);
            line_view.setState(x);
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


}

