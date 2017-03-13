package com.quascenta.petersroad.droidtag.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorCollection;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PreviewLineChartView;

/**
 * Created by AKSHAY on 3/2/2017.
 */

public class PreviewLineChart extends PreviewLineChartView {
    LineChartData data;

    public PreviewLineChart(Context context) {
        super(context);
    }

    public PreviewLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreviewLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setZoomEnabled(boolean isZoomEnabled) {
        super.setZoomEnabled(false);
    }

    public LineChartData generateDefaultData(SensorCollection sensorCollection, float lowerlimit, float upperlimit) {


        List<PointValue> values = new ArrayList<>();
        //   List<PointValue> values1 = new ArrayList<>();
        List<PointValue> values2 = new ArrayList<>();
        List<PointValue> values3 = new ArrayList<>();
        List<PointValue> values4 = new ArrayList<>();
        List<PointValue> values5 = new ArrayList<>();
        List<AxisValue> values6 = new ArrayList<>();

        for (int i = 0; i < sensorCollection.size(); ++i) {
            values.add(new PointValue(i, sensorCollection.get(i).getTemp_sensor_Sensor(0)).setLabel(String.format("%.1f", sensorCollection.get(i).getTemp_sensor_Sensor(0)) + " C"));
            // values1.add(new PointValue(i, sensorCollection.get(i).getTemp_sensor_Sensor(1)).setLabel(String.format("%.1f",sensorCollection.get(i).getTemp_sensor_Sensor(1))+" C"));
            values2.add(new PointValue(i, lowerlimit).setLabel("Limit(Start) = " + String.valueOf(lowerlimit) + "C"));
            values3.add(new PointValue(i, upperlimit).setLabel("Limit(End) = " + String.valueOf(upperlimit) + "C"));
            values4.add(new PointValue(i, 0));
            values5.add(new PointValue(i, upperlimit + 20.0f));
            values6.add(new AxisValue(i).setLabel(String.valueOf(sensorCollection.get(i).getMonth()) + " " + String.valueOf(sensorCollection.get(i).getDateTime().dayOfMonth().get())));
        }


        Line linex = generatePreviewLine(values);
        Line line4 = generateLineDefault(values4);
        Line line5 = generateLineDefault(values5);
        Line line6 = generatepreviewLimits(values2);
        Line line7 = generatepreviewLimits(values3);
        List<Line> lines1 = new ArrayList<>();
        lines1.add(linex);
        lines1.add(line6);
        lines1.add(line7);
        lines1.add(line4);
        lines1.add(line5);


        // prepare preview data, is better to use separate deep copy for preview chart.
        // Set color to grey to make preview area more visible.
        //data = new LineChartData(data);
        data = new LineChartData(lines1);
        data.getLines().get(0).setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
        this.setLineChartData(data);
        return data;
    }


    private Line generatePreviewLine(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.CYAN).setFilled(false).setHasPoints(false).setPointColor(Color.CYAN).setHasLabelsOnlyForSelected(false);
    }


    private Line generatepreviewLimits(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setFilled(false).setColor(Color.RED);
    }

    private Line generateLineDefault(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.TRANSPARENT);
    }


}

