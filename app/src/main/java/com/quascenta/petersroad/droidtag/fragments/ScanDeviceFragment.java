package com.quascenta.petersroad.droidtag.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;
import com.quascenta.petersroad.droidtag.Bluetooth.LeDeviceListAdapter;
import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 3/10/2017.
 */

public class ScanDeviceFragment extends Fragment {

    @Bind(R.id.listView)
    ListView scanLeDeviceList;


    BleDevice device;
    ProgressDialog progressDialog;

    LeDeviceListAdapter mLeDeviceListAdapter;

    @Subscribe(sticky = true)
    public void recieveStickyEvent(Events.Send_BLEDeviceForRegistration<BleDevice> s) {
        device = s.getContent();
        mLeDeviceListAdapter.addDevice(device);
        mLeDeviceListAdapter.notifyDataSetChanged();
        System.out.println(device.getmBleName());

    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().hasSubscriberForEvent(Events.Send_BLEDeviceForRegistration.class)) {
            EventBus.getDefault().register(this);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ConvertView = inflater.inflate(R.layout.fragment_scan, container, false);
        ButterKnife.bind(this, ConvertView);
        mLeDeviceListAdapter = new LeDeviceListAdapter(getContext());
        mLeDeviceListAdapter.addDevice(new BleDevice("Test", "00:00:00:00:0a"));
        scanLeDeviceList.setAdapter(mLeDeviceListAdapter);
        scanLeDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return ConvertView;
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}




