package com.quascenta.petersroad.droidtag.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by AKSHAY on 2/21/2017.
 */

public class LineView extends RelativeLayout {
    private static final float TOLERANCE = 5;
    public int width, height;
    Context context;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private Paint paint;
    private float mx, my;


    public LineView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(2f);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
