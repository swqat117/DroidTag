package com.quascenta.petersroad.droidtag.SensorCollection.model;

import com.pedrogomez.renderers.AdapteeCollection;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DeviceViewCollection implements AdapteeCollection<DeviceViewModel> {


    private final List<DeviceViewModel> deviceViewModelsList;
    private int i1 = 0;
    private LinkedList<SensorCollection> Monthly_sensors_collection_list;

    public DeviceViewCollection(DateTime startdate, DateTime enddate) {

        this.Monthly_sensors_collection_list = new LinkedList<SensorCollection>();
        this.deviceViewModelsList = new LinkedList<DeviceViewModel>();
        deviceViewModelsList.add(addSingle(1, "Device01", "MAA", startdate.minusMonths(3), enddate.minusMonths(3), "DEL", "Sensor", 2, 1, "Easy Solutions", "LoremIpsum Ltd"));
        System.out.println("Device 1 added");
        deviceViewModelsList.add(addSingle(2, "Device02", "BOM", startdate.minusMonths(5), enddate.minusMonths(5), "BLE", "Sensor", 2, 1, "LoremIpsum Ltd", "Convergence Tech"));
        System.out.println("Device 2 added");
        deviceViewModelsList.add(addSingle(3, "Device03", "BLE", startdate.minusMonths(1), enddate.minusMonths(1), "MAA", "Sensor", 2, 1, "Easy Solutions", "Arial"));
        System.out.println("Device 3 added");
        deviceViewModelsList.add(addSingle(4, "Device04", "DEL", startdate, enddate, "KOL", "Sensor", 2, 1, "Arial", "Hindustan Unilever"));
        System.out.println("Device 4 added");
        deviceViewModelsList.add(addSingle(5, "Device05", "GGN", startdate, enddate, "BOM", "Sensor", 2, 1, "Tata Consultancy Services", "Easy Solutions"));
        System.out.println("Device 5 added");
        deviceViewModelsList.add(addSingle(6, "Device06", "KOL", startdate.minusMonths(6), enddate.minusMonths(6), "GGN", "Sensor", 2, 1, "Easy Solutions", "Tata Consultancy Services"));
        System.out.println("------------------------------------------Completed");


    }

    public LinkedList<SensorCollection> setList() {
        return Monthly_sensors_collection_list;
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
//TODO need to sort through month view

    private DeviceViewModel addSingle(long id, String devicename, String from, DateTime startdate, DateTime enddate, String to, String sensortitle, int temp_no, int rh_no, String from_Company, String destination_company) {
        DeviceViewModel deviceViewModel = new DeviceViewModel(id, devicename, startdate, enddate, from, to, from_Company, destination_company);
        LinkedList<SensorCollection> monthsView = new LinkedList<SensorCollection>();
        for (LocalDate date = startdate.toLocalDate(); date.isBefore(enddate.toLocalDate()); date = date.plusDays(1)) {
            if (date.monthOfYear().get() == 1)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 2)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 3)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 4)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 5)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 6)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 7)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 8)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 9)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 10)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 11)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));
            if (date.monthOfYear().get() == 12)
                deviceViewModel.addSensor(new SensorViewModel(sensortitle + i1, date.toDateTimeAtCurrentTime(), temp_no, rh_no));

        }


        addMonths(deviceViewModel.getSensorCollection());

        return deviceViewModel;
    }

    private void addMonths(SensorCollection collection) {
        int x = collection.size();
        SensorCollection[] sensorCollections = new SensorCollection[12];
        LinkedList<SensorCollection> sensorCollectionArrayList = new LinkedList<SensorCollection>();
        for (int i = 0; i < x; i++) {

            if (collection.get(i).getDateTime().monthOfYear().get() == 1) {
                sensorCollections[0] = new SensorCollection();
                sensorCollections[0].add(collection.get(i));
            }
            if (collection.get(i).getDateTime().monthOfYear().get() == 2) {
                sensorCollections[1] = new SensorCollection();
                sensorCollections[1].add(collection.get(i));
            }
            if (collection.get(i).getDateTime().monthOfYear().get() == 3) {
                sensorCollections[2] = new SensorCollection();
                sensorCollections[2].add(collection.get(i));
            }
            if (collection.get(i).getDateTime().monthOfYear().get() == 4) {
                sensorCollections[3] = new SensorCollection();
                sensorCollections[3].add(collection.get(i));
            }
            if (collection.get(i).getDateTime().monthOfYear().get() == 5) {
                sensorCollections[4] = new SensorCollection();
                sensorCollections[4].add(collection.get(i));
            }
            if (collection.get(i).getDateTime().monthOfYear().get() == 6) {
                sensorCollections[5] = new SensorCollection();
                sensorCollections[5].add(collection.get(i));
            }
            if (collection.get(i).getDateTime().monthOfYear().get() == 7) {
                sensorCollections[6] = new SensorCollection();
                sensorCollections[6].add(collection.get(i));
            }
            if (collection.get(i).getDateTime().monthOfYear().get() == 8) {
                sensorCollections[7] = new SensorCollection();
                sensorCollections[7].add(collection.get(i));

                if (collection.get(i).getDateTime().monthOfYear().get() == 9) {
                    sensorCollections[8] = new SensorCollection();
                    sensorCollections[8].add(collection.get(i));
                }
                if (collection.get(i).getDateTime().monthOfYear().get() == 10) {
                    sensorCollections[9] = new SensorCollection();
                    sensorCollections[9].add(collection.get(i));
                }
                if (collection.get(i).getDateTime().monthOfYear().get() == 11) {
                    sensorCollections[10] = new SensorCollection();
                    sensorCollections[10].add(collection.get(i));
                }
                if (collection.get(i).getDateTime().monthOfYear().get() == 12) {
                    sensorCollections[11] = new SensorCollection();
                    sensorCollections[11].add(collection.get(i));
                }

                for (int j = 0; j < 12; j++) {
                    Monthly_sensors_collection_list.add(j, sensorCollections[j]);
                }


            }

        }


    }
}
