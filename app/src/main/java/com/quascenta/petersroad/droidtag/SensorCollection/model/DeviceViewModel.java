package com.quascenta.petersroad.droidtag.SensorCollection.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.ArrayList;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DeviceViewModel {
  private long DEVICE_ID;

  private String title;
    private DateTime start_time;

  private DateTime end_time;

  private String start_from;


    private int month_count;

  private String destination_to;

  private SensorCollection sensorCollection;

    private ArrayList<SensorCollection> month_collections;

    private String destination_company, start_company;
    private String[] ListOfMonths;

    public DeviceViewModel(long id, String title, DateTime start_time, DateTime end_time, String start_from, String destination_to, String destination_company, String start_company) {
        this.DEVICE_ID = id;
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_from = start_from;
        this.destination_to = destination_to;
        this.destination_company = destination_company;
        this.start_company = start_company;
        ListOfMonths = differenceinMonths();
        sensorCollection = new SensorCollection();


    }

    public void AddMonth(int i) {
        month_collections = new ArrayList<SensorCollection>();
        month_collections.add(i, sensorCollection);

    }

    public String[] getListOfMonths() {
        return ListOfMonths;
    }

    public void setListOfMonths(String[] listOfMonths) {
        ListOfMonths = listOfMonths;
    }

    public SensorCollection getMonthCollection(String _month) {
        SensorCollection month_Collection = new SensorCollection();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < sensorCollection.size(); i++) {
                    LocalDate date = sensorCollection.get(i).getDateTime().toLocalDate();
                    String month = date.monthOfYear().getAsShortText().toLowerCase();
                    if (month == _month) {

                        month_Collection.add(sensorCollection.get(i));
                        System.out.println("\n---------Date time" + sensorCollection.get(i).getDateTime().toString());
                        System.out.println("\n---------count-----" + i);
                    }
                }
            }
        }).start();

        return month_Collection;
    }

    public String getDestination_to() {
        return destination_to;
    }

    public void setDestination_to(String destination_to) {
        this.destination_to = destination_to;
    }

    public long getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(long DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public DateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(DateTime end_time) {
        this.end_time = end_time;
    }

    public SensorCollection getSensorCollection() {
        return sensorCollection;
    }

    public void setSensorCollection(SensorCollection sensorCollection) {
        this.sensorCollection = sensorCollection;
    }

    public String getStart_from() {
        return start_from;
    }

    public void setStart_from(String start_from) {
        this.start_from = start_from;
    }

    public DateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(DateTime start_time) {
        this.start_time = start_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination_company() {
        return destination_company;
    }

    public void setDestination_company(String destination_company) {
        this.destination_company = destination_company;
    }

    public String getStart_company() {
        return start_company;
    }

    public void setStart_company(String start_company) {
        this.start_company = start_company;
    }

    public void addSensor(SensorViewModel sensorViewModel) {
        sensorCollection.add(sensorViewModel);
    }

    public int getMonth_count() {
        return month_count;
    }

    public void setMonth_count(int month_count) {
        this.month_count = month_count;
    }

    public String[] differenceinMonths() {
        String[] x = new String[10];
        int i = 0;
        LocalDate l1 = start_time.toLocalDate();
        LocalDate l2 = end_time.toLocalDate();
        while (l1.isBefore(l2)) {

            System.out.println(l1.toString("MMM/yyyy"));
            x[month_count] = getMonth(l1.toDateTimeAtCurrentTime());
            month_count++;
            l1 = l1.plus(Period.months(1));


        }
        return x;

    }

    public String getMonth(DateTime dateTime) {
        int x = dateTime.getMonthOfYear();
        switch (x) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "";
        }
    }

    public String getMonth(int x) {

        switch (x) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "";
        }


    }
}
