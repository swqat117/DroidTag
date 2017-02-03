package com.quascenta.petersroad.droidtag.SensorCollection.ObjectGenerators;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DateTimeGenerator {

    private DateTime startDate;
    private DateTime endDate;
    private int difference;

    public DateTimeGenerator() {

    }


    public DateTimeGenerator(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        difference = Days.daysBetween(startDate, endDate).getDays();

    }


    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public void setgenerateRangeSpecificDates(int x) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        difference = x;
        if (startDate == null || endDate == null) {
            //TODO generate a constant list of scenarios using another class consts

            String x1 = "10/6/16 10:10:10";
            startDate = formatter.parseDateTime(x1);
            endDate = startDate.plusMonths(x);
        }
    }


}







