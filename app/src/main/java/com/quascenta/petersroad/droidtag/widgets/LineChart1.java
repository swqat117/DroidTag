package com.quascenta.petersroad.droidtag.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;

import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorCollection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by AKSHAY on 3/2/2017.
 */

public class LineChart1 extends LineChartView {


    LineChartData data;

    public LineChart1(Context context) {
        super(context);
    }

    public LineChart1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart1(Context context, AttributeSet attrs, int defStyle) {
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
        //Main Temperature line
        Line line = generateLine(values);
        //Limits
        Line line2 = generateLimits(values2);
        Line line3 = generateLimits(values3);
        //Default lines to set view port
        Line line4 = generateLineDefault(values4);
        Line line5 = generateLineDefault(values5);

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        lines.add(line5);
        // prepare preview data, is better to use separate deep copy for preview chart.
        // Set color to grey to make preview area more visible.
        //data = new LineChartData(data);
        data = new LineChartData(lines);
        data.setValueLabelTextSize(10);
        data.setValueLabelBackgroundEnabled(false);
        data.setAxisYLeft(new Axis().setHasLines(false));
        data.setAxisXBottom(new Axis(values6).setHasLines(false));
        this.setLineChartData(data);
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache(true);
        return data;
    }


    public void generateCopy() {


        final String filename = "chart-image.png";
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), filename);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            Bitmap bitmap = Bitmap.createBitmap(this.getDrawingCache());//important to make copy of that bitmap.
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void generateDefaultData1(SensorCollection sensorCollection, float lowerlimit, float upperlimit) {


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
            values4.add(new PointValue(i, lowerlimit));
            values5.add(new PointValue(i, upperlimit + 20.0f));
            values6.add(new AxisValue(i).setLabel(String.valueOf(sensorCollection.get(i).getMonth()) + " " + String.valueOf(sensorCollection.get(i).getDateTime().dayOfMonth().get())));
        }

        Line line = generateLine(values);
        Line line2 = generateLimits(values2);
        Line line3 = generateLimits(values3);
        Line line4 = generateLineDefault(values4);
        Line line5 = generateLineDefault(values5);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        // lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        lines.add(line5);
        // prepare preview data, is better to use separate deep copy for preview chart.
        // Set color to grey to make preview area more visible.
        //data = new LineChartData(data);
        data = new LineChartData(lines);
        data.setValueLabelTextSize(10);
        data.setValueLabelBackgroundEnabled(false);
        data.setAxisYLeft(new Axis().setHasLines(false));
        data.setAxisXBottom(new Axis(values6).setHasLines(false));
        this.setLineChartData(data);

    }

    private Line generateLine(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.CYAN).setFilled(false).setHasPoints(true).setPointColor(Color.CYAN).setHasLabels(true).setPointRadius(0);
    }

    private Line generatePreviewLine(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.CYAN).setFilled(false).setHasPoints(false).setPointColor(Color.CYAN).setHasLabelsOnlyForSelected(false);
    }

    private Line generateLimits(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(2).setFilled(false).setColor(Color.RED).setHasLabelsOnlyForSelected(true).setHasPoints(true).setPointRadius(2);
    }

    private Line generatepreviewLimits(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setFilled(false).setColor(Color.RED).setHasLabelsOnlyForSelected(true);
    }

    private Line generateLineDefault(List<PointValue> values) {
        return new Line(values).setHasPoints(false).setStrokeWidth(1).setColor(Color.TRANSPARENT);
    }


}
