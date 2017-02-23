package com.quascenta.petersroad.droidtag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewCollection;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.widgets.DashboardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 2/15/2017.
 */

public class ReportGenerationFragment extends Fragment {

    static int alert = 0, pending = 0, completed = 0;
    @Inject
    DeviceViewCollection deviceViewCollection;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    DashboardView dashboardView;

    private MultiCheckCategoryAdapter adapter;

    public static List<MultiCheckCategory> makeMultiCheckCategories(DeviceViewCollection deviceViewCollection) {
        return Arrays.asList(makeMultiAllDevicesCheckCategory(deviceViewCollection), make_Completed_DevicesCheckCategory(deviceViewCollection), make_Alerted_DevicesCheckCategory(deviceViewCollection), make_notUploaded_DevicesCheckCategory(deviceViewCollection));
    }

    public static List<DeviceViewModel> make_allDevices(DeviceViewCollection deviceViewCollection) {
        List<DeviceViewModel> deviceViewModels = new ArrayList<>();

        for (int i = 0; i < deviceViewCollection.size(); i++) {
            deviceViewModels.add(deviceViewCollection.get(i));
        }

        return deviceViewModels;

    }

    public static List<DeviceViewModel> make_CompletedDevices(DeviceViewCollection deviceViewCollection) {
        List<DeviceViewModel> deviceViewModels = new ArrayList<>();

        for (int i = 0; i < deviceViewCollection.size(); i++) {
            if (deviceViewCollection.get(i).getStatus() == 1) {
                deviceViewModels.add(deviceViewCollection.get(i));
                completed++;

            }
        }

        return deviceViewModels;

    }

    public static List<DeviceViewModel> make_AlertedDevices(DeviceViewCollection deviceViewCollection) {
        List<DeviceViewModel> deviceViewModels = new ArrayList<>();

        for (int i = 0; i < deviceViewCollection.size(); i++) {
            if (deviceViewCollection.get(i).getStatus() == 2)
                deviceViewModels.add(deviceViewCollection.get(i));
            alert++;
        }

        return deviceViewModels;

    }

    public static List<DeviceViewModel> make_DataNotUploadedDevices(DeviceViewCollection deviceViewCollection) {
        List<DeviceViewModel> deviceViewModels = new ArrayList<>();

        for (int i = 0; i < deviceViewCollection.size(); i++) {
            if (deviceViewCollection.get(i).getStatus() == 0)
                deviceViewModels.add(deviceViewCollection.get(i));
            pending++;
        }

        return deviceViewModels;

    }

    public static MultiCheckCategory makeMultiAllDevicesCheckCategory(DeviceViewCollection deviceViewCollection) {

        return new MultiCheckCategory("All Devices", make_allDevices(deviceViewCollection), R.drawable.ic_launcher);
    }

     /*  public static List<MultiCheckGenre> makeMultiCheckGenres() {
        return Arrays.asList(makeMultiCheckRockGenre(),
                makeMultiCheckJazzGenre(),
                makeMultiCheckClassicGenre(),
                makeMultiCheckSalsaGenre(),
                makeMulitCheckBluegrassGenre());
    }*/

   /* public static List<SingleCheckGenre> makeSingleCheckGenres() {
        return Arrays.asList(makeSingleCheckRockGenre(),
                makeSingleCheckJazzGenre(),
                makeSingleCheckClassicGenre(),
                makeSingleCheckSalsaGenre(),
                makeSingleCheckBluegrassGenre());

                public static List<Artist> makeJazzArtists() {
        Artist milesDavis = new Artist("Miles Davis", true);
        Artist ellaFitzgerald = new Artist("Ella Fitzgerald", true);
        Artist billieHoliday = new Artist("Billie Holiday", false);
        return Arrays.asList(milesDavis, ellaFitzgerald, billieHoliday);
    }
                 public static MultiCheckGenre makeMultiCheckJazzGenre() {
        return new MultiCheckGenre("Jazz", makeJazzArtists(), R.drawable.ic_saxophone);
    }
    }*/

    public static MultiCheckCategory make_Completed_DevicesCheckCategory(DeviceViewCollection deviceViewCollection) {

        return new MultiCheckCategory("Completed Devices", make_CompletedDevices(deviceViewCollection), R.drawable.ic_launcher);
    }

    public static MultiCheckCategory make_Alerted_DevicesCheckCategory(DeviceViewCollection deviceViewCollection) {

        return new MultiCheckCategory("Alerted Devices", make_AlertedDevices(deviceViewCollection), R.drawable.ic_launcher);
    }

    public static MultiCheckCategory make_notUploaded_DevicesCheckCategory(DeviceViewCollection deviceViewCollection) {

        return new MultiCheckCategory("Not Uploaded", make_DataNotUploadedDevices(deviceViewCollection), R.drawable.ic_launcher);
    }

 /*   @OnClick(R.id.clear_button)
    public void onclick() {
        adapter.clearChoices();
    }

    @OnClick(R.id.check_first_child)
    public void oncheck() {
        adapter.checkChild(true, 0, 3);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ConvertView = inflater.inflate(R.layout.fragment_multicheck, container, false);
        ButterKnife.bind(this, ConvertView);
        ((MyApp) getActivity().getApplication()).getComponent().inject(this);
        dashboardView = (DashboardView) ConvertView.findViewById(R.id.dashboard_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new MultiCheckCategoryAdapter(makeMultiCheckCategories(deviceViewCollection));
        recyclerView.setLayoutManager(layoutManager);
        initDashBoard(deviceViewCollection);
        recyclerView.setAdapter(adapter);


        return ConvertView;
    }

    public void initDashBoard(DeviceViewCollection deviceViewCollection) {
        dashboardView.setDevices_All(100, deviceViewCollection.size());
        dashboardView.setDevices_alerted(deviceViewCollection.size(), 2);
        dashboardView.setDevices_pending(deviceViewCollection.size(), 2);
        dashboardView.setDevices_completed(deviceViewCollection.size(), 2);


    }





    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

}
