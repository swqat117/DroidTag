package com.quascenta.petersroad.droidtag.Bluetooth.bleApi;

import android.bluetooth.BluetoothGatt;

/**
 * Created by Akshay on 2016/11/29.
 */

public interface iQppCallback {
    void onQppReceiveData(BluetoothGatt mBluetoothGatt, String qppUUIDForNotifyChar, byte[] qppData);
}
