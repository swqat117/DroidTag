package com.quascenta.petersroad.droidtag.widgets;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.EventBus.GlobalBus;
import com.quascenta.petersroad.droidtag.R;

import org.greenrobot.eventbus.Subscribe;

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

    boolean x1 = false, y1 = false;
    int x = 0, y = 0;
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

    }

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        x = 25;
        y = 55;


        View contentView = View.inflate(getContext(), R.layout.export_bottomsheet, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
        GlobalBus.getBus().register(this);
        s_lowerlimit.setMax(y);
        s_upperlimit.setProgress(y);
        lowerlimit1.setText(String.valueOf(y));
        upperlimit1.setText(String.valueOf(100));
        s_upperlimit.setProgress(y);
        s_lowerlimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (i >= seekBar.getMax() && i > s_upperlimit.getProgress()) {
                    lowerlimit.setText("Limit (Start)  " + String.valueOf(seekBar.getMax()));
                    s_upperlimit.setProgress(i);
                    Toast.makeText(getContext(), "Lower limit cannot be set greater to that of higherlimit, Please Change the value", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                x = seekBar.getProgress();
                if (seekBar.getProgress() >= s_upperlimit.getProgress())
                    lowerlimit.setText("Limit (Start)  " + String.valueOf(seekBar.getProgress()));
                s_upperlimit.setProgress(seekBar.getProgress());
                Toast.makeText(getContext(), "Higher is set greater than lower,Hence value is reset", Toast.LENGTH_SHORT).show();


            }
        });

        s_upperlimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (i < s_lowerlimit.getProgress()) {
                    upperlimit.setText("Limit (End)  " + String.valueOf(i));
                    i = s_lowerlimit.getProgress();
                } else {

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if (seekBar.getProgress() > s_lowerlimit.getProgress()) {
                    y = seekBar.getProgress();
                    s_lowerlimit.setMax(y);
                    upperlimit.setText("Limit (End)  " + String.valueOf(y));
                } else if (seekBar.getProgress() < s_lowerlimit.getProgress()) {
                    seekBar.setProgress(s_lowerlimit.getProgress());
                    upperlimit.setText("Limit (End)  " + String.valueOf(s_lowerlimit.getProgress()));
                    Toast.makeText(getContext(), "Resetting lowerlimit to its original" + String.valueOf(s_lowerlimit.getProgress()), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
        //Write code to perform action after event is received.
        x = ((int) activityFragmentMessage.getLow_limit());
        y = ((int) activityFragmentMessage.getHigh_limit());

    }


}
