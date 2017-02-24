package com.quascenta.petersroad.droidtag.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 2/24/2017.
 */

public class Profilepage extends RelativeLayout {

    onTextViewClickListener onTextViewClickListener;

    @Bind(R.id.user_profile_photo)
    ImageButton photobutton;

    @Bind(R.id.user_profile_name)
    TextView tv_username;
    @Bind(R.id.user_profile_short_bio)
    TextView tv_userdata;


    public Profilepage(Context context) {
        super(context);
        init();
    }


    public Profilepage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Profilepage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Profilepage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.layout_profile, this);
        ButterKnife.bind(this);
    }

    public void setOnTextViewClickListener(onTextViewClickListener onTextViewClickListener) {
        this.onTextViewClickListener = onTextViewClickListener;

    }

    public interface onTextViewClickListener {

        void sendIntent();

    }


}
