package com.yan.custom.views.watch.touch;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.yan.custom.views.watch.actors.IActor;
import com.yan.custom.views.watch.math.WatchMathUtils;

import java.util.List;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchTouchProcessor {

    private IActor mCurrentDraggedActor;
    private List<IActor> mTouchableActors;
    private PointF mViewSize;
    private PointF mCacheTouchPoint;
    private PointF mViewOrigin;

    public WatchTouchProcessor(List<IActor> touchableActors) {
        mTouchableActors = touchableActors;
        mCacheTouchPoint = new PointF();
        mViewOrigin = new PointF();
        mViewSize = new PointF(0, 0);
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
        for (IActor touchableActor : mTouchableActors) {
            if (processTouchOnActor(touchX, touchY, touchableActor)) break;
        }
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