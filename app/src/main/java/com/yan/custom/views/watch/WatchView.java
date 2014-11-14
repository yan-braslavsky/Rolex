package com.yan.custom.views.watch;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yan.custom.views.R;
import com.yan.custom.views.watch.actors.BaseActor;
import com.yan.custom.views.watch.actors.IActor;
import com.yan.custom.views.watch.renderer.ActorCanvasRenderer;
import com.yan.custom.views.watch.touch.WatchTouchProcessor;

import java.util.ArrayList;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchView extends View {

    private ActorCanvasRenderer mActorCanvasRenderer;
    private WatchTouchProcessor mWatchTouchProcessor;
    private PointF mViewCenterPoint;

    //Actors
    private BaseActor mHourArrowActor;
    private BaseActor mWatchStrapActor;
    private ArrayList<IActor> mActorsList;

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
        mActorsList = new ArrayList<IActor>();
        mViewCenterPoint = new PointF();
        mActorCanvasRenderer = new ActorCanvasRenderer();
        mHourArrowActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.hour));
        mWatchTouchProcessor = new WatchTouchProcessor(mHourArrowActor);
        mWatchStrapActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.strap));

        addActorsToView();
    }

    private void addActorsToView() {
        //The last one added will be the first one visible
        mActorsList.add(mWatchStrapActor);
        mActorsList.add(mHourArrowActor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewCenterPoint.x = w / 2;
        mViewCenterPoint.y = h / 2;
        mWatchTouchProcessor.setViewSize(w, h);

        //TODO : Change actor sizes

        //Arrow
        mHourArrowActor.setTranslation(-mHourArrowActor.getBitmap().getWidth() / 2, -mHourArrowActor.getBitmap().getHeight());
        float left = mViewCenterPoint.x + mHourArrowActor.getTranslation().x;
        float top = mViewCenterPoint.y + mHourArrowActor.getTranslation().y;
        mHourArrowActor.setBounds(left, top, left + mHourArrowActor.getBitmap().getWidth(), top + mHourArrowActor.getBitmap().getHeight());

        //strap
        mWatchStrapActor.setTranslation(-mWatchStrapActor.getBitmap().getWidth() / 2, -mWatchStrapActor.getBitmap().getHeight() / 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (IActor actor : mActorsList) {
            mActorCanvasRenderer.renderActorOnCanvas(actor, canvas);
//            mActorCanvasRenderer.renderActorOnCanvas(mHourArrowActor, canvas);
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