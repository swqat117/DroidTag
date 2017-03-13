package com.quascenta.petersroad.droidtag.Bluetooth.BleVO;

import android.bluetooth.BluetoothDevice;

import com.quascenta.petersroad.droidtag.Bluetooth.BleConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * If you need to set the Bluetooth object more properties and behavior can be inherited from the class     such as extends BleDevice
 */

public class BleDevice {

    public final static String TAG = BleDevice.class.getSimpleName();

    //Is connected
    private boolean isConnected = false;
    //Connection Status: 2503 Not Connected       2504 Connected       2505 Connected      2506 Disconnected
    private int mConnectionState = BleConfig.DISCONNECT;

    //Bluetooth address
    private String mBleAddress;

    //Bluetooth name
    private String mBleName;
    //Bluetooth modified name
    private String mBleAlias;
    private List<BleDevice> mConnectedDevices = new ArrayList<>();


    public BleDevice(BluetoothDevice device) {
        this.mBleAddress = device.getAddress();
        this.mBleName = device.getName();
    }

    public BleDevice(String Name, String mBleAddress) {
        this.mBleAddress = mBleAddress;
        this.mBleName = Name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public int getConnectionState() {
        return mConnectionState;
    }

    public void setConnectionState(int mConnectionState) {
        this.mConnectionState = mConnectionState;
    }

    public String getBleAddress() {
        return mBleAddress;
    }

    public void setBleAddress(String mBleAddress) {
        this.mBleAddress = mBleAddress;
    }

    public String getmBleName() {
        return mBleName;
    }

    public void setBleName(String mBleName) {
        this.mBleName = mBleName;
    }

    public String getBleAlias() {
        return mBleAlias;
    }

    public void setBleAlias(String mBleAlias) {
        this.mBleAlias = mBleAlias;
    }
}
