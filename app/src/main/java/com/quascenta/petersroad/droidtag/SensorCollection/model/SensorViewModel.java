package com.quascenta.petersroad.droidtag.SensorCollection.model;

import org.joda.time.DateTime;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class SensorViewModel {

    private  String title;
    private DateTime dateTime;
    private  float[] temp_sensor;
    private  int number_of_temp_sensors;
    private  int number_of_rh_sensors;
    private float[] rh_sensor;


    public SensorViewModel(String title,DateTime dateTime, int number_of_temp_sensors,int number_of_rh_sensors){
        this.title = title;
        this.dateTime = dateTime;
        this.number_of_temp_sensors = number_of_temp_sensors;
        this.number_of_rh_sensors = number_of_rh_sensors;
        temp_sensor = new float[number_of_temp_sensors];
        rh_sensor = new float[number_of_rh_sensors];
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getNumber_of_rh_sensors() {
        return number_of_rh_sensors;
    }

    public void setNumber_of_rh_sensors(int number_of_rh_sensors) {
        this.number_of_rh_sensors = number_of_rh_sensors;
    }

    public int getNumber_of_temp_sensors() {
        return number_of_temp_sensors;
    }

    public void setNumber_of_temp_sensors(int number_of_temp_sensors) {
        this.number_of_temp_sensors = number_of_temp_sensors;
    }

    public float getRh_sensor_sensor(int x) {
        return rh_sensor[x];
    }

    public float[] getRh_sensor() {
        return rh_sensor;
    }

    public void setRh_sensor(float[] rh_sensor) {
        this.rh_sensor = rh_sensor;
    }

    public float getTemp_sensor_Sensor(int x) {
        return temp_sensor[x];
    }

    public float[] getTemp_sensor_all() {
        return temp_sensor;
    }

    public void setTemp_sensor(float[] temp_sensor) {
        this.temp_sensor = temp_sensor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
