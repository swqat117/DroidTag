package com.quascenta.petersroad.droidtag.widgets;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.EventBus.GlobalBus;
import com.quascenta.petersroad.droidtag.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Bind(R.id.bsf_sb_upperlimit)
    SeekBar s_upperlimit;

    @Bind(R.id.bsf_sb_lowerlimit)
    SeekBar s_lowerlimit;

    int x, y;
    Events.FragmentActivityMessage fragmentActivityMessageEvent;

    public BottomSheetFragment() {
        super();
    }

    public void getLimits(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        GlobalBus.getBus().unregister(this);

    }

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.export_bottomsheet, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
        GlobalBus.getBus().register(this);

        fragmentActivityMessageEvent = new Events.FragmentActivityMessage();
        s_lowerlimit.setProgress(x);
        s_upperlimit.setProgress(y);
        s_lowerlimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i >= y) {
                    seekBar.setProgress(y);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() <= y) {
                    fragmentActivityMessageEvent = new Events.FragmentActivityMessage();
                    fragmentActivityMessageEvent.setLow_limit(seekBar.getProgress());

                }


            }
        });

        s_upperlimit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i <= x) {
                    seekBar.setProgress(x);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() >= x) {
                    fragmentActivityMessageEvent.setHigh_limit(seekBar.getProgress());

                }
            }
        });
        GlobalBus.getBus().post(fragmentActivityMessageEvent);
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
        //Write code to perform action after event is received.
        x = ((int) activityFragmentMessage.getLow_limit());
        y = ((int) activityFragmentMessage.getHigh_limit());

    }


}
