package com.quascenta.petersroad.droidtag.SensorCollection.ObjectGenerators;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewCollection;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;

/**
 * Created by AKSHAY on 2/3/2017.
 */

public class ParentLevel extends BaseExpandableListAdapter {
    public static final String TAG = "ParentLevel";
    public static final int FIRST_LEVEL_COUNT = 10;
    public static final int SECOND_LEVEL_COUNT = 4;
    public static final int THIRD_LEVEL_COUNT = 20;
    TextView text;
    SecondLevelExpandableListView secondLevelELV;
    String x[] = new String[10];
    private Context context;
    private DeviceViewCollection deviceViewCollection;
    private DeviceViewModel deviceViewModel;
    private String MONTH;
    public ParentLevel(Context context, DeviceViewCollection deviceViewCollection) {
        this.context = context;
        this.deviceViewCollection = deviceViewCollection;

    }

    public ParentLevel(Context context, DeviceViewModel tvShow) {
        this.context = context;
        deviceViewModel = tvShow;


    }

    @Override
    public Object getChild(int arg0, int arg1) {
        return arg1;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        secondLevelELV.setAdapter(new SecondLevelAdapter(context));
        secondLevelELV.setGroupIndicator(null);
        return secondLevelELV;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return SECOND_LEVEL_COUNT;
    }

    @Override
    public Object getGroup(int groupPosition) {
        System.out.println("Id ;" + groupPosition);
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return deviceViewModel.getMonth_count();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            text = (TextView) convertView.findViewById(R.id.sensor_number);
            TextView text2 = (TextView) convertView.findViewById(R.id.sensor_temp_value1);
            TextView text3 = (TextView) convertView.findViewById(R.id.sensor_rh_value1);
            String x[] = new String[10];
            x = deviceViewModel.getListOfMonths();
            text.setText(x[groupPosition]);
            text2.setText(String.format("%.2f", deviceViewModel.getSensorCollection().get(0).getTemp_sensor_Sensor(0)));
            text3.setText(String.format("%.2f", deviceViewModel.getSensorCollection().get(0).getTemp_sensor_Sensor(1)));






        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}