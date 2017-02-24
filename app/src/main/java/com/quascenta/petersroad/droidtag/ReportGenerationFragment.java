package com.quascenta.petersroad.droidtag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewCollection;
import com.quascenta.petersroad.droidtag.widgets.DashboardView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 2/15/2017.
 */

public class ReportGenerationFragment extends Fragment {


    @Bind(R.id.all_loggers)
    TextView tv_all;
    @Bind(R.id.alerted_loggers)
    TextView tv_alerted;
    @Bind(R.id.Completed_loggers)
    TextView tv_complete;
    @Bind(R.id.pending_loggers)
    TextView tv_pending;
    @Inject
    DeviceViewCollection deviceViewCollection;

    Profilepage profileview;
    DashboardView dashboardView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ConvertView = inflater.inflate(R.layout.fragment_multicheck, container, false);
        ButterKnife.bind(this, ConvertView);
        ((MyApp) getActivity().getApplication()).getComponent().inject(this);
        profileview = initProfile(ConvertView);
        dashboardView = initDashBoard(deviceViewCollection, ConvertView);
        return ConvertView;
    }


    @OnClick(R.id.all_loggers)
    public void onAllClick() {


    }

    @OnClick(R.id.alerted_loggers)
    public void onAlertedClick() {


    }

    @OnClick(R.id.pending_loggers)
    public void onPendingClick() {


    }

    @OnClick(R.id.Completed_loggers)
    public void onCompletedClick() {


    }


    public Profilepage initProfile(View view) {
        profileview = (Profilepage) view.findViewById(R.id.profile_view);
        return profileview;
    }


    public DashboardView initDashBoard(DeviceViewCollection deviceViewCollection, View view) {
        dashboardView = (DashboardView) view.findViewById(R.id.dashboard_view);
        dashboardView.setDevices_All(100, deviceViewCollection.size());
        dashboardView.setDevices_alerted(deviceViewCollection.size(), 2);
        dashboardView.setDevices_pending(deviceViewCollection.size(), 2);
        dashboardView.setDevices_completed(deviceViewCollection.size(), 2);
        return dashboardView;


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




