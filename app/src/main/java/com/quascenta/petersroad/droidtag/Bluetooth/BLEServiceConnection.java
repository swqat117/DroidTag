package com.quascenta.petersroad.droidtag.Bluetooth;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by AKSHAY on 3/10/2017.
 */

public class BLEServiceConnection implements ServiceConnection {
    //try adding weak reference for the current object kater
    BluetoothLeService bluetoothLeService;
    private String TAG = getClass().getSimpleName();

    public BLEServiceConnection(BluetoothLeService service) {
        bluetoothLeService = service;
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        bluetoothLeService = ((BluetoothLeService.LocalBinder) iBinder).getService();
        Log.e(TAG, "");
        if (!bluetoothLeService.initialize()) {
            Log.e(TAG, "Unable to initialize Bluetooth");

        }
        if (bluetoothLeService != null) {
        }
        // Automatically connects to the device upon successful start-up
        // initialization.
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        bluetoothLeService = null;
    }


}
