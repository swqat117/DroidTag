package com.quascenta.petersroad.droidtag.Renderer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.SensorCollection.model.SensorViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class SensorRenderer extends Renderer<SensorViewModel> {

    @Bind(R.id.sensor_number)
    TextView sensorNumberTextView;

    @Bind(R.id.sensor_temp_value1)
    TextView sensorTempValue1;
    @Bind(R.id.sensor_rh_value1)
    TextView sensorRHvalue1;

    private int position;

    /**
     * Configure the position associated to this renderer.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Apply ButterKnife inject method to support view injections.
     */
    @Override
    protected void setUpView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void hookListeners(View view) {
        //Empty
    }

    /**
     * Inflate the layout associated to this renderer
     */
    @Override
    protected View inflate(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.sensor_row, viewGroup, false);
    }

    /**
     * Render the EpisodeViewModel information.
     */
    @Override
    public void render() {
        SensorViewModel sensorViewModel = getContent();
        sensorNumberTextView.setText(String.valueOf(sensorViewModel.getDateTime().getDayOfMonth()));

        //// TODO: 1/31/2017 check if datetime is saving the value from device view model
        sensorRHvalue1.setText(String.format("%.2f", sensorViewModel.getTemp_sensor_Sensor(1)));
        sensorTempValue1.setText(String.format("%.2f", sensorViewModel.getTemp_sensor_Sensor(1)));

    }
}
