package com.yan.custom.views.watch;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yan.custom.views.R;
import com.yan.custom.views.watch.actors.ArrowActor;
import com.yan.custom.views.watch.renderer.ActorCanvasRenderer;
import com.yan.custom.views.watch.touch.WatchTouchProcessor;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchView extends View {


    private ArrowActor mHourArrowActor;
    private ActorCanvasRenderer mActorCanvasRenderer;
    private WatchTouchProcessor mWatchTouchProcessor;

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


        mActorCanvasRenderer = new ActorCanvasRenderer();
        mHourArrowActor = new ArrowActor(BitmapFactory.decodeResource(getResources(), R.drawable.hour));

        mWatchTouchProcessor = new WatchTouchProcessor(mHourArrowActor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWatchTouchProcessor.setViewSize(w,h);

        //Change actor sizes
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mActorCanvasRenderer.renderActorOnCanvas(mHourArrowActor, canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mWatchTouchProcessor.processTouch(event);

        //TODO : not always need to invalidate
        invalidate();
        return true;
    }

}