package com.quascenta.petersroad.droidtag.SensorCollection.model;

import org.joda.time.DateTime;

import java.util.Random;

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
    private int count;

    public SensorViewModel() {

    }

    public SensorViewModel(String title, DateTime dateTime, int number_of_temp_sensors, int number_of_rh_sensors) {
        this.title = title;
        this.dateTime = dateTime;
        this.number_of_temp_sensors = number_of_temp_sensors;
        this.number_of_rh_sensors = number_of_rh_sensors;
        temp_sensor = generateSensorValues();
        rh_sensor = new float[number_of_rh_sensors];
        count = 0;
    }

    public static float[] floatTemp(int x, int min, int max) {
        float[] temp = new float[x];
        for (int i = 0; i < x; i++) {
            temp[i] = (min + max) / 2 + new Random().nextFloat();
        }
        return temp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public float[] generateSensorValues() {
        if (number_of_temp_sensors != 0) {

            return floatTemp(number_of_temp_sensors, 25, 55);
        }
        return null;
    }

    public float[] generateSensorValues(int l, int min, int max) {
        if (l == number_of_temp_sensors) {

            temp_sensor = floatTemp(number_of_temp_sensors, min, max);
        }
        return temp_sensor;
    }

    public String getMonth() {
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

}
