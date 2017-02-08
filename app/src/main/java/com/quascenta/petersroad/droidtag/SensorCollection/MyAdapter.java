package com.quascenta.petersroad.droidtag.SensorCollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorCollection;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by AKSHAY on 2/8/2017.
 */

public class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    SensorCollection MonthCollection;
    int count = 0;
    private String[] countries;
    private LayoutInflater inflater;

    public MyAdapter(Context context, DeviceViewModel deviceViewModelCollection) {
        inflater = LayoutInflater.from(context);
        this.MonthCollection = deviceViewModelCollection.getSensorCollection();

        //  countries = context.getResources().getStringArray(R.array.countries);
    }


    @Override
    public int getCount() {
        return MonthCollection.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sensor_row, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.sensor_number);
            holder.text1 = (TextView) convertView.findViewById(R.id.sensor_month);
            holder.text2 = (TextView) convertView.findViewById(R.id.sensor_temp_value1);
            holder.text3 = (TextView) convertView.findViewById(R.id.sensor_rh_value1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(String.valueOf(MonthCollection.get(position).getDateTime().getDayOfMonth()));
        holder.text1.setText(String.valueOf(MonthCollection.get(position).getMonth()));
        holder.text2.setText(String.format("%.2f", MonthCollection.get(position).getTemp_sensor_Sensor(0)));
        holder.text3.setText(String.format("%.2f", MonthCollection.get(position).getTemp_sensor_Sensor(1)));
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text1 = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + String.valueOf(MonthCollection.get(position).getMonth());
        holder.text1.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon

        return MonthCollection.get(position).getMonth().subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text1;

    }

    class ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text;
    }

}
