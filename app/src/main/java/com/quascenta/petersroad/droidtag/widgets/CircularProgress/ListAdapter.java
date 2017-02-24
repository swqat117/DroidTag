package com.quascenta.petersroad.droidtag.widgets.CircularProgress;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.widgets.Line_view;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 2/27/2017.
 */

public class ListAdapter
        extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final TypedValue mTypedValue = new TypedValue();
    private final OnItemClickListener onItemClickListener;
    private Context context;
    private int mBackground;
    private List<DeviceViewModel> devices;

    public ListAdapter(Context context, List<DeviceViewModel> devices, OnItemClickListener listener) {

        this.context = context;
        mBackground = mTypedValue.resourceId;
        this.devices = devices;
        onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_show_row, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(devices.get(position), onItemClickListener, position);


    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public String getValueAt(int position) {
        return devices.get(position).toString();
    }


    public interface OnItemClickListener {
        void onItemClick(DeviceViewModel item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        String mBoundString;
        GradientDrawable drawable = new GradientDrawable();
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
        Line_view line_view;
        boolean x = true;

        ViewHolder(View view) {
            super(view);
            mView = view;
            line_view = (Line_view) view.findViewById(R.id.line4);
            ButterKnife.bind(this, view);


        }

        public void bind(final DeviceViewModel deviceViewModel, final OnItemClickListener l, int i) {
            mSource_company_name.setText(deviceViewModel.getStart_company());
            mDestination_company_name.setText(deviceViewModel.getDestination_company());
            mSource_location.setText(deviceViewModel.getStart_from());
            mDestination_location.setText(deviceViewModel.getDestination_to());
            customer_tracking_id.setText("#1021110" + String.valueOf(deviceViewModel.getDEVICE_ID()));
            line_view.setState(deviceViewModel.getStatus());


            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    toggle();
                    l.onItemClick(deviceViewModel);


                }
            });
        }


        public void toggle() {


            drawable.setShape(GradientDrawable.RECTANGLE);
            if (x) {
                mView.setBackground(null);
                drawable.setColor(Color.parseColor("#839DDA"));
                mView.setBackground(drawable);
                x = false;
            } else {
                mView.setBackground(null);
                drawable.setColor(Color.TRANSPARENT);
                mView.setBackground(drawable);
                x = true;
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }


}