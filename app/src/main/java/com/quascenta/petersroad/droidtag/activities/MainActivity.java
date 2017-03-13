package com.quascenta.petersroad.droidtag.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.Bluetooth.BLEServiceConnection;
import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;
import com.quascenta.petersroad.droidtag.Bluetooth.BluetoothLeService;
import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.fragments.ReportGenerationFragment;
import com.quascenta.petersroad.droidtag.fragments.ScanDeviceFragment;
import com.quascenta.petersroad.droidtag.widgets.CustomViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Bluetooth LE SERVICE ";


    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private static final int REQUEST_ENABLE_BT = 1;
    protected static String uuidQppService = "0000fee9-0000-1000-8000-00805f9b34fb";
    protected static String uuidQppCharWrite = "d44bc439-abfd-45a2-b575-925416129600";
    @Bind(R.id.btn_next)
    Button next;
    @Bind(R.id.btn_skip)
    Button skip;
    @Bind(R.id.layoutDots)
    LinearLayout linearLayout;
    ScanDeviceFragment fragment;
    private BluetoothGattCharacteristic mWriteCharacteristic;
    private int mPermissionIdx = 0x10;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothLeService mBluetoothLeService;
    private boolean mScanning;
    private Handler mHandler = new Handler();
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BleDevice b = new BleDevice(device);

                        }
                    });
                }
            };

    @OnClick(R.id.btn_next)
    public void onClickNext() {


    }

    @OnClick(R.id.btn_skip)
    public void setSkip() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        fragment = new ScanDeviceFragment();
        setContentView(R.layout.activity_register_ble_device);

        //
        init();
        new ProgressOperation(this).execute();

    }

    public void setUpProgress(ProgressDialog dialog) {

        dialog.setIndeterminate(true);
        dialog.setMessage("Starting scan");
        dialog.setTitle("Scanning  for Devices...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    public void showProgress(ProgressDialog dialog, String x) {
        dialog.setMessage(x);
    }

    public boolean postAck(BleDevice b) {
        EventBus.getDefault().postSticky(new Events.Send_BLEDeviceForRegistration<BleDevice>(b));

        return true;
    }

    @SuppressWarnings("deprecation")
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.startLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    public boolean initBleDevice() {

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            requestPermission(new String[]{Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION}, getString(R.string.ask_permission), new BaseActivity.GrantedResult() {
                @Override
                public void onResult(boolean granted) {
                    if (!granted) {
                        finish();
                    } else {
                        bindService();

                    }

                }
            });
        }
        return true;
    }


    private void bindService() {
        try {
            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            boolean bll = bindService(gattServiceIntent, new BLEServiceConnection(mBluetoothLeService),
                    BIND_AUTO_CREATE);
            if (bll) {
                System.out.println("---------------");
            } else {
                System.out.println("===============");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void init() {

        final ActionBar ab = getSupportActionBar();
        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        viewPager.setPagingEnabled(true);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragment, "Scan Loggers");
        adapter.addFragment(new ReportGenerationFragment(), "REPORT");

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

    }

    BleDevice setUpDummyBleDevice(String name, String address, int x) {
        BleDevice device = new BleDevice(name, address);
        device.setConnected(true);
        device.setConnectionState(x);  //Connected - 2504 ,2505
        return device;
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    class ProgressOperation extends AsyncTask<Void, String, Void> {
        ProgressDialog progressDialog;

        public ProgressOperation(Context context) {
            super();
            progressDialog = new ProgressDialog(context);

        }

        @Override
        protected void onPreExecute() {
            setUpProgress(progressDialog);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {

                String x = initBleDevice() ? "Device Found" : "Searching for Device...";
                publishProgress(x);
                Thread.sleep(500);
                publishProgress("Scanning for Loggers");
                Thread.sleep(1000);
                publishProgress("Loggers obtained ");
                Thread.sleep(500);

                // 2504 -  connected state


            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            postAck(setUpDummyBleDevice("Quintic BLE", "24:0A:45:aa:2d", 2504));
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            showProgress(progressDialog, values[0]);
        }
    }
}


