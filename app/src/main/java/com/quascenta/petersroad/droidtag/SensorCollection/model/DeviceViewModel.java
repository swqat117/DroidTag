package com.quascenta.petersroad.droidtag.SensorCollection.model;

import org.joda.time.DateTime;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DeviceViewModel {
  private long DEVICE_ID;

  private String title;

  private DateTime start_time;

  private DateTime end_time;

  private String start_from;

  private String destination_to;

  private SensorCollection sensorCollection;


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

    public DeviceViewModel(long id, String title, DateTime start_time, DateTime end_time, String start_from, String destination_to, SensorCollection sensorCollection){
                this.DEVICE_ID = id;
                this.title = title;
                this.start_time = start_time;
                this.end_time = end_time;
                this.start_from = start_from;
                this.destination_to = destination_to;
                this.sensorCollection = sensorCollection;
            }






}
