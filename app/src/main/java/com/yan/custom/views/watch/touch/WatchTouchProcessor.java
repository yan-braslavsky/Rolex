package com.yan.custom.views.watch.touch;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.yan.custom.views.watch.actors.IActor;
import com.yan.custom.views.watch.math.WatchMathUtils;

import java.util.List;

/**
 * Created by Yan on 11/14/2014.
 * <p/>
 * This class receives touch events from the {@link com.yan.custom.views.watch.WatchView}
 * and decides how to respond on them.It also has access to actors of the {@link com.yan.custom.views.watch.WatchView}
 * that suppose to react on touches.
 */
public class WatchTouchProcessor {

    /**
     * Defines a listener that is triggered on actor
     * dragging.
     */
    public interface IActorDragListener {
        void onActorDragBegin(IActor draggedActor);

        void onActorDrag(IActor draggedActor);

        void onActorDragEnd(IActor draggedActor);
    }

    private IActor mCurrentDraggedActor;
    private List<IActor> mTouchableActors;
    private IActorDragListener mActorDragListener;

    //we are caching some values to avoid not necessary allocations
    //during touch processing
    private PointF mCachedTouchPoint;
    private PointF mViewOrigin;
    private PointF mViewSize;


    /**
     * The order of touchable actors defines the order in which they will
     * be processed.
     *
     * @param touchableActors actors that will be processed for touches.
     */
    public WatchTouchProcessor(List<IActor> touchableActors) {
        mTouchableActors = touchableActors;
        mCachedTouchPoint = new PointF();
        mViewOrigin = new PointF();
        mViewSize = new PointF(0, 0);
    }

    /**
     * Receives a touch event and takes the popper action.
     *
     * @param event
     */
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

        //notify listener of end drag
        if (mActorDragListener != null) {
            mActorDragListener.onActorDragEnd(mCurrentDraggedActor);
        }
        mCurrentDraggedActor = null;
    }

    private void onTouchMove(float touchX, float touchY) {
        if (mCurrentDraggedActor != null) {
            mActorDragListener.onActorDrag(mCurrentDraggedActor);
            float rotation = getAngle(touchX, touchY);
            //rotation is calculated relative to X axis
            //this is why it should be offset by 90 degrees.
            rotation += 90;
            mCurrentDraggedActor.setRotation(rotation);
        }
    }

    private void onTouchDown(float touchX, float touchY) {

        //sequentially process touch on every actor
        for (IActor touchableActor : mTouchableActors) {
            //when actor collides with the touch we will break and not process
            //other actors
            if (processTouchOnActor(touchX, touchY, touchableActor)) break;
        }
    }

    private boolean processTouchOnActor(float touchX, float touchY, IActor actor) {

        //cache the touch point
        mCachedTouchPoint.x = touchX;
        mCachedTouchPoint.y = touchY;

        //rotate the touch point back like if the touch point was on the original position of the actor
        WatchMathUtils.rotatePointAroundOrigin(mCachedTouchPoint, mViewOrigin, -actor.getRotation());

        if (actor.getCollider().contains(mCachedTouchPoint.x, mCachedTouchPoint.y)) {
            mCurrentDraggedActor = actor;

            //notify listener of begin drag
            if (mActorDragListener != null) {
                mActorDragListener.onActorDragBegin(actor);
            }

            float rotation = getAngle(touchX, touchY);
            //rotation is calculated relative to X axis
            //this is why it should be offset by 90 degrees.
            rotation += 90;
            actor.setRotation(rotation);
            return true;
        }
        return false;
    }

    /**
     * Calculates the touch point angle.
     *
     * @return the angle between X axis and the touch point
     */
    public float getAngle(float viewTouchPointX, float viewTouchPointY) {

        //delta X between touch point and the origin
        double dx = viewTouchPointX - (mViewSize.x / 2);

        //delta Y between touch point and the origin
        //invert the y axis
        double dy = -(viewTouchPointY - (mViewSize.y / 2));

        //calculate angle in radians
        double inRads = Math.atan2(dy, dx);

        //adapt the angle to positive value
        inRads = (inRads < 0) ? Math.abs(inRads) : (2 * Math.PI - inRads);

        //return result in degrees
        return (float) (Math.toDegrees(inRads));
    }

    public void setViewSize(int w, int h) {
        //here we are caching values
        //for later usage
        mViewSize.x = w;
        mViewSize.y = h;
        mViewOrigin.x = w / 2;
        mViewOrigin.y = h / 2;
    }

    /**
     * Listener will be notified every time {@link com.yan.custom.views.watch.actors.IActor}
     * is dragged.
     */
    public void setActorDragListener(IActorDragListener dragListener) {
        mActorDragListener = dragListener;
    }
}