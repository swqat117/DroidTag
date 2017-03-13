package com.quascenta.petersroad.droidtag.Bluetooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;
import com.quascenta.petersroad.droidtag.R;

import java.util.ArrayList;


/**
 * Created by admin on 2016/11/26.
 */

// Adapter for holding devices found through scanning.
public class LeDeviceListAdapter extends BaseAdapter {
    private ArrayList<BleDevice> mLeDevices;
    private LayoutInflater mInflator;

    public LeDeviceListAdapter(Context context) {
        super();
        mLeDevices = new ArrayList<BleDevice>();
        mInflator = LayoutInflater.from(context);
    }

    public void addDevice(BleDevice device) {
       /* if (!mLeDevices.contains(device)) {
*/
        mLeDevices.add(device);


        // }
    }

    public BleDevice getDevice(int position) {
        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.listitem_device1, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
            viewHolder.deviceState = (TextView) view.findViewById(R.id.device_state);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (mLeDevices.get(i).getConnectionState() == 2504) {
            viewHolder.deviceState.setText("Connecting...");
        }
        if (mLeDevices.get(i).isConnected()) {
            viewHolder.deviceState.setText("Connected");
            viewHolder.deviceAddress.setText((mLeDevices.get(i).getBleAddress()));
        } else {
            viewHolder.deviceState.setText("Not Connected");
        }
        if (mLeDevices.get(i).getmBleName() != null && mLeDevices.get(i).getmBleName().length() > 0) {
            viewHolder.deviceName.setText(mLeDevices.get(i).getmBleName());
            viewHolder.deviceAddress.setText(mLeDevices.get(i).getBleAddress());
        }    //viewHolder.deviceRSSI.setText(deviceRSSI);
        else
            viewHolder.deviceName.setText(R.string.unknown_device);


        return view;

    }

    private class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        TextView deviceState;
    }

}
