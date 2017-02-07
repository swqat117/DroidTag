package com.quascenta.petersroad.droidtag.SensorCollection.model;

import com.pedrogomez.renderers.AdapteeCollection;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DeviceViewCollection implements AdapteeCollection<DeviceViewModel> {


    private final List<DeviceViewModel> deviceViewModelsList;
    private int i1 = 0;


    public DeviceViewCollection(DateTime startdate, DateTime enddate) {
        this.deviceViewModelsList = new LinkedList<DeviceViewModel>();


        deviceViewModelsList.add(addSingle(2, "Device01", "MAA", startdate.minusMonths(3), enddate.minusMonths(3), "DEL", "Sensor", 2, 1, "Easy Solutions", "LoremIpsum Ltd"));
        System.out.println("Device 1 added");
        deviceViewModelsList.add(addSingle(3, "Device02", "BOM", startdate.minusMonths(5), enddate.minusMonths(5), "BLE", "Sensor", 2, 1, "LoremIpsum Ltd", "Convergence Tech"));
        System.out.println("Device 2 added");
        deviceViewModelsList.add(addSingle(4, "Device03", "BLE", startdate.minusMonths(1), enddate.minusMonths(1), "MAA", "Sensor", 2, 1, "Easy Solutions", "Arial"));
        System.out.println("Device 3 added");
        deviceViewModelsList.add(addSingle(5, "Device04", "DEL", startdate, enddate, "KOL", "Sensor", 2, 1, "Arial", "Hindustan Unilever"));
        System.out.println("Device 4 added");
        deviceViewModelsList.add(addSingle(6, "Device05", "GGN", startdate, enddate, "BOM", "Sensor", 2, 1, "Tata Consultancy Services", "Easy Solutions"));
        System.out.println("Device 5 added");
        deviceViewModelsList.add(addSingle(7, "Device06", "KOL", startdate.minusMonths(6), enddate.minusMonths(6), "GGN", "Sensor", 2, 1, "Easy Solutions", "Tata Consultancy Services"));
        System.out.println("------------------------------------------Completed");
    }




    @Override
    public int size() {
        return deviceViewModelsList.size();
    }

    @Override
    public DeviceViewModel get(int index) {
        return deviceViewModelsList.get(index);
    }

    @Override
    public boolean add(DeviceViewModel element) {
        if (element != null) {
            deviceViewModelsList.add(element);
            return true;
        }
        return false;

    }

    @Override
    public boolean remove(Object element) {
        deviceViewModelsList.remove(element);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends DeviceViewModel> elements) {
        deviceViewModelsList.addAll(elements);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> elements) {
        return false;
    }

    @Override
    public void clear() {

    }


    private DeviceViewModel addSingle(long id, String devicename, String from, DateTime startdate, DateTime enddate, String to, String sensortitle, int temp_no, int rh_no, String from_Company, String destination_company) {
        DeviceViewModel deviceViewModel = new DeviceViewModel(id, devicename, startdate, enddate, from, to, from_Company, destination_company);
        ArrayList<SensorViewModel> monthsView = new ArrayList<>();
        for (LocalDate date = startdate.toLocalDate(); date.isBefore(enddate.toLocalDate()); date = date.plusDays(1)) {
            if (date.monthOfYear().getAsShortText().toLowerCase().equals("jan"))
                monthsView.add(date.getDayOfMonth(), new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));

        }
        return deviceViewModel;
    }
}
