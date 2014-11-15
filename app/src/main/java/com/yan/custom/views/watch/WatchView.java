package com.yan.custom.views.watch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yan.custom.views.watch.actors.IActor;
import com.yan.custom.views.watch.managers.ActorManager;
import com.yan.custom.views.watch.renderer.ActorCanvasRenderer;
import com.yan.custom.views.watch.time.IWatchTime;
import com.yan.custom.views.watch.time.WatchTime;
import com.yan.custom.views.watch.touch.WatchTouchProcessor;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchView extends View {

    public interface WindingWheelRotateListener {
        void onWindingWheelRotationBegin(float currentRotationAngle);
        void onWindingWheelRotationEnd(float currentRotationAngle);
    }

    private static final int TOTAL_HOURS_ON_WATCH_SCREEN = 12;
    private static final int TOTAL_MINUTES_ON_WATCH_SCREEN = 60;
    private static final int TOTAL_SECONDS_ON_WATCH_SCREEN = 60;

    //rotations
    private static final double TWO_PI = (Math.PI * 2);
    private static final float DEGREES_FOR_HOUR = (float) Math.toDegrees(TWO_PI / TOTAL_HOURS_ON_WATCH_SCREEN);
    private static final float DEGREES_FOR_MINUTE = (float) Math.toDegrees(TWO_PI / TOTAL_MINUTES_ON_WATCH_SCREEN);
    private static final float DEGREES_FOR_SECOND = (float) Math.toDegrees(TWO_PI / TOTAL_SECONDS_ON_WATCH_SCREEN);

    private ActorCanvasRenderer mActorCanvasRenderer;
    private WatchTouchProcessor mWatchTouchProcessor;
    private PointF mViewCenterPoint;
    private ActorManager mActorManager;
    private WatchTime mWatchTime;
    private WatchTouchProcessor.IActorDragListener mActorDragListener;
    private WindingWheelRotateListener mWindingWheelRotateListener;

    public WatchView(Context context) {
        super(context);
        init();
    }

    public WatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initActorDragListener();
        mWatchTime = new WatchTime(0, 0, 0);
        mViewCenterPoint = new PointF();
        mActorCanvasRenderer = new ActorCanvasRenderer();
        mActorManager = new ActorManager(getResources());
        mWatchTouchProcessor = new WatchTouchProcessor(mActorManager.getTouchableActors());
        mWatchTouchProcessor.setActorDragListener(mActorDragListener);
    }

    private void initActorDragListener() {
        mActorDragListener = new WatchTouchProcessor.IActorDragListener() {
            @Override
            public void onActorDragBegin(IActor draggedActor) {
                if (draggedActor == mActorManager.getWindingWheel()) {
                    //Notify winding wheel listener
                    if (mWindingWheelRotateListener != null) {
                        mWindingWheelRotateListener.onWindingWheelRotationBegin(draggedActor.getRotation());
                    }
                }
            }

            @Override
            public void onActorDrag(IActor draggedActor) {
                //when arrows are dragged , the time should be reset.
                if (draggedActor == mActorManager.getHoursArrowActor()) {
                    mWatchTime.setHours((int) ((draggedActor.getRotation() % 360) / DEGREES_FOR_HOUR));
                } else if (draggedActor == mActorManager.getMinutesArrowActor()) {
                    mWatchTime.setMinutes((int) ((draggedActor.getRotation() % 360) / DEGREES_FOR_MINUTE));
                } else if (draggedActor == mActorManager.getSecondsArrowActor()) {
                    mWatchTime.setSeconds((int) ((draggedActor.getRotation() % 360) / DEGREES_FOR_SECOND));
                }
            }

            @Override
            public void onActorDragEnd(IActor draggedActor) {
                if (draggedActor == mActorManager.getWindingWheel()) {
                    //Notify winding wheel listener
                    if (mWindingWheelRotateListener != null) {
                        mWindingWheelRotateListener.onWindingWheelRotationEnd(draggedActor.getRotation());
                    }
                }
            }
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewCenterPoint.x = w / 2;
        mViewCenterPoint.y = h / 2;
        mWatchTouchProcessor.setViewSize(w, h);
        mActorManager.setViewSize(w, h);
        mActorManager.recalculateActorsMetrics();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (IActor actor : mActorManager.getActorsDisplayList()) {
            mActorCanvasRenderer.renderActorOnCanvas(actor, canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mWatchTouchProcessor.processTouch(event);
        invalidate();
        return true;
    }

    public void setTime(int hours, int minutes, int seconds) {
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    public void setHours(int hours) {
        //limit the input to acceptable parameters
        mWatchTime.setHours(hours % TOTAL_HOURS_ON_WATCH_SCREEN);
        //set rotation of the hour arrow according to the time
        mActorManager.getHoursArrowActor().setRotation(DEGREES_FOR_HOUR * mWatchTime.getHours());
    }

    public void setMinutes(int minutes) {
        //limit the input to acceptable parameters
        mWatchTime.setMinutes(minutes % TOTAL_MINUTES_ON_WATCH_SCREEN);
        //set rotation of the minutes arrow according to the time
        mActorManager.getMinutesArrowActor().setRotation(DEGREES_FOR_MINUTE * mWatchTime.getMinutes());
    }

    public void setSeconds(int seconds) {
        //limit the input to acceptable parameters
        mWatchTime.setSeconds(seconds % TOTAL_SECONDS_ON_WATCH_SCREEN);
        //set rotation of the seconds arrow according to the time
        mActorManager.getSecondsArrowActor().setRotation(DEGREES_FOR_SECOND * mWatchTime.getSeconds());
    }

    public IWatchTime getTime() {
        return mWatchTime;
    }

    public void setWatchTimeChangeListener(WatchTime.WatchTimeChangeListener watchTimeChangeListener) {
        //delegate the listener to watch time
        mWatchTime.setWatchTimeChangeListener(watchTimeChangeListener);
    }

    public void setWindingWheelRotateListener(WindingWheelRotateListener windingWheelRotateListener) {
        mWindingWheelRotateListener = windingWheelRotateListener;
    }
}