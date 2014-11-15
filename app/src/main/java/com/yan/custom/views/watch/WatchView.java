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
import com.yan.custom.views.watch.touch.WatchTouchProcessor;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchView extends View {

    private ActorCanvasRenderer mActorCanvasRenderer;
    private WatchTouchProcessor mWatchTouchProcessor;
    private PointF mViewCenterPoint;
    private ActorManager mActorManager;

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
        mViewCenterPoint = new PointF();
        mActorCanvasRenderer = new ActorCanvasRenderer();
        mActorManager = new ActorManager(getResources());
        mWatchTouchProcessor = new WatchTouchProcessor(mActorManager.getTouchableActors());
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

        //TODO : not always need to invalidate
        invalidate();
        return true;
    }

}