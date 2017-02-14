package com.quascenta.petersroad.droidtag.widgets;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 2/10/2017.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment {
    @Bind(R.id.bsf_tv_title)
    TextView title;
    @Bind(R.id.bsf_ok)
    TextView ok;
    @Bind(R.id.bsf_tv_upperlimit)
    TextView upperlimit;
    @Bind(R.id.bsf_tv_lowerlimit)
    TextView lowerlimit;
    @Bind(R.id.bsf_tv_highervalue)
    TextView upperlimit1;
    @Bind(R.id.bsf_tv_higherlvaue)
    TextView lowerlimit1;



    @Bind(R.id.bsf_sb_upperlimit)
    SeekBar s_upperlimit;
    @Bind(R.id.bsf_sb_lowerlimit)
    SeekBar s_lowerlimit;


    int x, x2 = 0, y, y2 = 0;
    Events.FragmentActivityMessage fragmentActivityMessageEvent;

    public void getLimits(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();



    }

    @OnClick(R.id.bsf_ok)
    public void click() {

        EventBus.getDefault().post(new Events.ActivityFragmentMessage((float) x2, (float) y2));
        super.dismiss();
    }

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //TODO recieve x and y values as arguements
        x = 25;
        y = 55;

        View contentView = View.inflate(getContext(), R.layout.export_bottomsheet, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);

        s_lowerlimit.setProgress(x);
        s_upperlimit.setProgress(y);
        lowerlimit.setText("Limit (Start)  " + String.valueOf(x));
        upperlimit.setText("Limit (End)    " + String.valueOf(y));
        lowerlimit1.setText(String.valueOf(100));
        upperlimit1.setText(String.valueOf(100));
        s_upperlimit.setProgress(y);
        s_lowerlimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


                lowerlimit.setText("Limit (Start)  " + String.valueOf(i));

                }




            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                //IF lower limit is greater than the upper limit  reset it back to its previous value
                if (seekBar.getProgress() >= s_upperlimit.getProgress()) {
                    if (x >= s_upperlimit.getProgress()) {
                        //Resetting back to original
                        s_upperlimit.setProgress(y);
                        seekBar.setProgress(25);
                        lowerlimit.setText("Limit (Start)  " + String.valueOf(x));
                        upperlimit.setText("Limit (End)    " + String.valueOf(y));
                        Toast.makeText(getContext(), "Invalid Data . Values being reset " + String.valueOf(s_upperlimit.getProgress()), Toast.LENGTH_SHORT).show();
                    }
                    //Resetting only the lower limit
                    else {
                        s_lowerlimit.setProgress(x);
                        lowerlimit.setText("Limit (Start)  " + String.valueOf(x));
                    }

                } else {
                    x2 = seekBar.getProgress();
                    lowerlimit.setText("Limit (Start)  " + String.valueOf(x2));
                }
            }
        });

        s_upperlimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                upperlimit.setText("Limit (End)    " + String.valueOf(i));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() <= s_lowerlimit.getProgress()) {

                    //Resetting the original value recieved
                    if (y < s_lowerlimit.getProgress()) {
                        seekBar.setProgress(y);
                        s_lowerlimit.setProgress(x);
                        lowerlimit.setText("Limit (Start)  " + String.valueOf(x));
                        upperlimit.setText("Limit (End)    " + String.valueOf(y));
                        Toast.makeText(getContext(), "Invalid Data . Values being reset to " + String.valueOf(s_upperlimit.getProgress()), Toast.LENGTH_SHORT).show();
                } else {
                        seekBar.setProgress(y);
                        upperlimit.setText("Limit (End)    " + String.valueOf(y));
                    }
                } else {

                    y2 = seekBar.getProgress();
                    upperlimit.setText("Limit (End)    " + String.valueOf(seekBar.getProgress()));
                }

            }
        });

    }




}
