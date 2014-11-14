package com.yan.custom.views.watch.touch;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.yan.custom.views.watch.actors.ArrowActor;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchTouchProcessor {

    private ArrowActor mHourArrowActor;
    private PointF mViewSize;

    public WatchTouchProcessor(ArrowActor hourArrowActor) {
        mHourArrowActor = hourArrowActor;
        mViewSize = new PointF(0, 0);
    }


    public void processTouch(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        float rotation = (float) getAngle(new PointF(touchX, touchY));
        rotation += 90;

        mHourArrowActor.setRotation(rotation);
    }

    public double getAngle(PointF screenPoint) {
        double dx = screenPoint.x - (mViewSize.x / 2);
        double dy = -(screenPoint.y - (mViewSize.y / 2));

        double inRads = Math.atan2(dy, dx);

        if (inRads < 0)
            inRads = Math.abs(inRads);
        else
            inRads = 2 * Math.PI - inRads;

        return Math.toDegrees(inRads);
    }

    public void setViewSize(int w, int h) {
        mViewSize.x = w;
        mViewSize.y = h;
    }
}
