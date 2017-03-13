package com.quascenta.petersroad.droidtag.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.MyApp;
import com.quascenta.petersroad.droidtag.PdfGenerator.PdfGenerator;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.widgets.CircularProgress.ListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportListActivity extends AppCompatActivity {

    public static final int STATUS_NOT_UPLOADED = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_ALERT = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private final String TAG = "com.quascenta.petersroad.droidtag.activities";
    String path;
    int anInt = 1;
    @Bind(R.id.lists_rv)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton generate_Report;
    List<DeviceViewModel> deviceViewModels = new ArrayList<>();
    List<DeviceViewModel> Report_deviceViewModels = new ArrayList<>();
    ListAdapter adapter;
    PdfGenerator pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        ButterKnife.bind(this);

        ((MyApp) getApplication()).getComponent().inject(this);
        getPermissions();


    }

    @OnClick(R.id.fab)
    protected void onClicktoGeneratePdf() {
        pdf = new PdfGenerator(deviceViewModels, this);

        try {
            new MyTask(this).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Snackbar.make(getApplicationContext(),Report_deviceViewModels.size()+"Devices added",Snackbar.LENGTH_SHORT).show();
    }


    private void setupRecyclerView(RecyclerView recyclerView, List<DeviceViewModel> deviceViewModels) {
        adapter = new ListAdapter(this, deviceViewModels, new ListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(DeviceViewModel item) {
                ValidateClick(item, Report_deviceViewModels);
            }

        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }



    @Override
    protected void onStart() {

        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onMessage(Events.DeviceList fragmentMessage) {
        deviceViewModels = fragmentMessage.getList();
        setupRecyclerView(recyclerView, deviceViewModels);
    }


    void ValidateClick(DeviceViewModel item, List<DeviceViewModel> report) {

        if (!item.isEnabled()) {
            item.setEnabled(true);
            report.add(item);
            generate_Report.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), " " + Report_deviceViewModels.size() + "Device(s) selected", Toast.LENGTH_SHORT).show();
        } else if (item.isEnabled()) {
            item.setEnabled(false);
            if (report != null) {
                Log.d("Report item", "removed");
                generate_Report.setVisibility(View.INVISIBLE);
                report.remove(item);
            } else {
                Toast.makeText(getApplicationContext(), "Nothing added", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void open(File file) {
        //File path
        if (file.exists()) //Checking for the file is exist or not
        {
            Uri path1 = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path1, "application/pdf");
            startActivity(objIntent);//Staring the pdf viewer
        } else {

            Toast.makeText(getApplicationContext(), "The file not exists! ", Toast.LENGTH_SHORT).show();

        }
    }

    public void open_File(File file) throws Exception {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent1 = Intent.createChooser(intent, "Open With");
        try {
            startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }


    public boolean getPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            return true;
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            }
        return false;
    }


    private class MyTask extends AsyncTask<Integer, Integer, Boolean> {

        File file = null;
        private String TAG = "LongTask.java";
        private String url;
        private ProgressDialog p;
        private String filename;


        public MyTask(Context ctx) {

            this.p = new ProgressDialog(ctx);

        }


        @Override
        protected Boolean doInBackground(Integer... strings) {

            try {
                file = pdf.generateFile();
                path = file.getAbsolutePath();
                System.out.println(path);
                if (!file.canRead()) {
                    if (!file.mkdirs()) {
                        Log.d(TAG, "Directory was not created");
                    }
                    return false;
                } else {
                    Log.d(TAG, "Path is " + path);
                    Log.d(TAG, "Passed do in Background in true mode");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            p.dismiss();
            try {
                open_File(file);
                Log.d(TAG, "File Opened");
            } catch (Exception e) {
                Log.e(TAG, "onPostExecute exception");
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.setMessage("Saving PDF to Disk");
            p.setIndeterminate(false);
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}



