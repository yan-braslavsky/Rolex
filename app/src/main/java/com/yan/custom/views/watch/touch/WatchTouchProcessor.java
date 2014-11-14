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

    public WatchTouchProcessor(IActor hoursArrowActor, IActor windingWheel) {
        mHourArrowActor = hoursArrowActor;
        mWindingWheel = windingWheel;
        mViewSize = new PointF(0, 0);
        mCacheTouchPoint = new PointF();
        mViewOrigin = new PointF();
    }


    public void processTouch(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        mCacheTouchPoint.x = touchX;
        mCacheTouchPoint.y = touchY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(touchX, touchY);
                break;
        }


    }

    private void onTouchUp(float touchX, float touchY) {
        //TODO:
        mCurrentDraggedActor = null;
    }

    private void onTouchMove(float touchX, float touchY) {
        //TODO:

        if (mCurrentDraggedActor != null) {
            float rotation = getAngle(new PointF(touchX, touchY));
            rotation += 90;
            mCurrentDraggedActor.setRotation(rotation);
        }

    }

    private void onTouchDown(float touchX, float touchY) {


        if (mWindingWheel.getCollider().contains(touchX,touchY)) {
            mCurrentDraggedActor = mWindingWheel;

            //TODO : extract to concrete handler ?
            float rotation = getAngle(new PointF(touchX, touchY));
            rotation += 90;
            mWindingWheel.setRotation(rotation);
            return;
        }

        //rotate back like if the touch point was on the original position of the actor
        WatchMathUtils.rotatePointAroundOrigin(mCacheTouchPoint, mViewOrigin, -mHourArrowActor.getRotation());

        if (mHourArrowActor.getCollider().contains(mCacheTouchPoint.x, mCacheTouchPoint.y)) {
            mCurrentDraggedActor = mHourArrowActor;

            //TODO : extract to concrete handler ?
            float rotation = getAngle(new PointF(touchX, touchY));
            rotation += 90;
            mHourArrowActor.setRotation(rotation);
            return;
        }
    }

    public float getAngle(PointF screenPoint) {
        double dx = screenPoint.x - (mViewSize.x / 2);
        double dy = -(screenPoint.y - (mViewSize.y / 2));
        double inRads = Math.atan2(dy, dx);
        inRads = (inRads < 0) ? Math.abs(inRads) : (2 * Math.PI - inRads);
        return (float) (Math.toDegrees(inRads));
    }

    public void setViewSize(int w, int h) {
        mViewSize.x = w;
        mViewSize.y = h;
        mViewOrigin.x = w / 2;
        mViewOrigin.y = h / 2;
    }


}
