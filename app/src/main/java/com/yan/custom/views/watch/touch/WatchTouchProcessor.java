package com.yan.custom.views.watch.touch;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.yan.custom.views.watch.actors.IActor;
import com.yan.custom.views.watch.math.WatchMathUtils;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchTouchProcessor {

    private IActor mHourArrowActor;
    private PointF mViewSize;
    private PointF mCacheTouchPoint;
    private PointF mViewOrigin;
    private IActor mCurrentDraggedActor;
    private IActor mWindingWheel;
    private IActor mMinutesArrowActor;
    private IActor mSecondsArrowActor;

    public WatchTouchProcessor(IActor windingWheel, IActor secondsArrowActor, IActor minutesArrowActor, IActor hoursArrowActor) {
        mHourArrowActor = hoursArrowActor;
        mWindingWheel = windingWheel;
        mViewSize = new PointF(0, 0);
        mCacheTouchPoint = new PointF();
        mViewOrigin = new PointF();
        mMinutesArrowActor = minutesArrowActor;
        mSecondsArrowActor = secondsArrowActor;
    }


    public void processTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(), event.getY());
                break;
        }
    }

    private void onTouchUp(float touchX, float touchY) {
        mCurrentDraggedActor = null;
    }

    private void onTouchMove(float touchX, float touchY) {
        if (mCurrentDraggedActor != null) {
            float rotation = getAngle(new PointF(touchX, touchY));
            rotation += 90;
            mCurrentDraggedActor.setRotation(rotation);
        }
    }

    private void onTouchDown(float touchX, float touchY) {

        //Winding wheel
        if (processTouchOnActor(touchX, touchY, mWindingWheel)) return;

        //Seconds arrow
        if (processTouchOnActor(touchX, touchY, mSecondsArrowActor)) return;

        //Minute arrow
        if (processTouchOnActor(touchX, touchY, mMinutesArrowActor)) return;

        //Hour arrow
        if (processTouchOnActor(touchX, touchY, mHourArrowActor)) return;

    }

    private boolean processTouchOnActor(float touchX, float touchY, IActor arrow) {
        mCacheTouchPoint.x = touchX;
        mCacheTouchPoint.y = touchY;
        //rotate back like if the touch point was on the original position of the actor
        WatchMathUtils.rotatePointAroundOrigin(mCacheTouchPoint, mViewOrigin, -arrow.getRotation());

        if (arrow.getCollider().contains(mCacheTouchPoint.x, mCacheTouchPoint.y)) {
            mCurrentDraggedActor = arrow;
            float rotation = getAngle(new PointF(touchX, touchY));
            rotation += 90;
            arrow.setRotation(rotation);
            return true;
        }
        return false;
    }

    public float getAngle(PointF screenPoint) {
        double dx = screenPoint.x - (mViewSize.x / 2);
        double dy = -(screenPoint.y - (mViewSize.y / 2));
        double inRads = Math.atan2(dy, dx);
        inRads = (inRads < 0) ? Math.abs(inRads) : (2 * Math.PI - inRads);
        float result = (float) (Math.toDegrees(inRads));
        return result;
    }

    public void setViewSize(int w, int h) {
        mViewSize.x = w;
        mViewSize.y = h;
        mViewOrigin.x = w / 2;
        mViewOrigin.y = h / 2;
    }
}