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
    private ArrayList<IActor> mActorsList;

    //Background actors
    private BaseActor mWatchStrapActor;
    private BaseActor mWindingWheel;
    private BaseActor mWatchScreenActor;

    //Arrows actors
    private BaseActor mHoursArrowActor;
    private BaseActor mMinutesArrowActor;
    private BaseActor mSecondsArrowActor;

    //Big time actors
    private BaseActor mTwelweOClockActor;
    private BaseActor mThreeOClockActor;
    private BaseActor mSixOClockActor;
    private BaseActor mNineOClockActor;

    //Small time actors
    private BaseActor mOneOClockActor;
    private BaseActor mTwoOClockActor;
    private BaseActor mFourOClockActor;
    private BaseActor mFiveOClockActor;
    private BaseActor mSevenOClockActor;
    private BaseActor mEightOClockActor;
    private BaseActor mTenOClockActor;
    private BaseActor mElevenOClockActor;


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
        createActors();
        addActorsToView();
        mWatchTouchProcessor = new WatchTouchProcessor(mHoursArrowActor);
    }

    private void createActors() {

        mWatchStrapActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.strap));
        mWindingWheel = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.wheel));
        mWatchScreenActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.screen));

        //arrows
        mHoursArrowActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.hour));
        mMinutesArrowActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.min));
        mSecondsArrowActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.sek));

        //time
        //big times
        mTwelweOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.b_line));
        mThreeOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.b_line));
        mSixOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.b_line));
        mNineOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.b_line));

        //small times
        mOneOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
        mTwoOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
        mFourOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
        mFiveOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
        mSevenOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
        mEightOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
        mTenOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
        mElevenOClockActor = new BaseActor(BitmapFactory.decodeResource(getResources(), R.drawable.s_line));
    }

    private void addActorsToView() {
        //The last one added will be the first one visible
        mActorsList.add(mWatchStrapActor);
        mActorsList.add(mWindingWheel);
        mActorsList.add(mWatchScreenActor);

        //big time markers
        mActorsList.add(mTwelweOClockActor);
        mActorsList.add(mThreeOClockActor);
        mActorsList.add(mSixOClockActor);
        mActorsList.add(mNineOClockActor);

        //small time markers
        mActorsList.add(mOneOClockActor);
        mActorsList.add(mTwoOClockActor);
        mActorsList.add(mFourOClockActor);
        mActorsList.add(mFiveOClockActor);
        mActorsList.add(mSevenOClockActor);
        mActorsList.add(mEightOClockActor);
        mActorsList.add(mTenOClockActor);
        mActorsList.add(mElevenOClockActor);

        //arrows
        mActorsList.add(mHoursArrowActor);
        mActorsList.add(mMinutesArrowActor);
        mActorsList.add(mSecondsArrowActor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewCenterPoint.x = w / 2;
        mViewCenterPoint.y = h / 2;
        mWatchTouchProcessor.setViewSize(w, h);

        //Hours arrow
        mHoursArrowActor.setTranslation(-mHoursArrowActor.getBitmap().getWidth() / 2, -mHoursArrowActor.getBitmap().getHeight());
        float left = mViewCenterPoint.x + mHoursArrowActor.getTranslation().x;
        float top = mViewCenterPoint.y + mHoursArrowActor.getTranslation().y;
        mHoursArrowActor.setBounds(left, top, left + mHoursArrowActor.getBitmap().getWidth(), top + mHoursArrowActor.getBitmap().getHeight());

        //Minutes arrow
        mMinutesArrowActor.setTranslation(-mMinutesArrowActor.getBitmap().getWidth() / 2, -mMinutesArrowActor.getBitmap().getHeight());

        //Seconds arrow
        mSecondsArrowActor.setTranslation(-mSecondsArrowActor.getBitmap().getWidth() / 2, -mSecondsArrowActor.getBitmap().getHeight());

        //strap
        mWatchStrapActor.setTranslation(-mWatchStrapActor.getBitmap().getWidth() / 2, -mWatchStrapActor.getBitmap().getHeight() / 2);

        //wheel
        mWindingWheel.setTranslation(-mWindingWheel.getBitmap().getWidth() / 2, -mWindingWheel.getBitmap().getHeight() / 2);

        //screen
        mWatchScreenActor.setTranslation(-mWatchScreenActor.getBitmap().getWidth() / 2, -mWatchScreenActor.getBitmap().getHeight() / 2);

        initBigTimeMarkers();
        initSmallTimeMarkers();

    }

    private void initSmallTimeMarkers() {
        //big time markers
        float smallHourMarkerYTranslation = -(mWatchScreenActor.getBitmap().getHeight() / 2f - mOneOClockActor.getBitmap().getHeight());
        float smallHourMarkerXTranslation = -mOneOClockActor.getBitmap().getWidth() / 2f;
        float oneHourRotation = 30f;

        //one
        mOneOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mOneOClockActor.setRotation(oneHourRotation * 1);

        //two
        mTwoOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mTwoOClockActor.setRotation(oneHourRotation * 2);

        //four
        mFourOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mFourOClockActor.setRotation(oneHourRotation * 4);

        //five
        mFiveOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mFiveOClockActor.setRotation(oneHourRotation * 5);

        //seven
        mSevenOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mSevenOClockActor.setRotation(oneHourRotation * 7);

        //eight
        mEightOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mEightOClockActor.setRotation(oneHourRotation * 8);

        //ten
        mTenOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mTenOClockActor.setRotation(oneHourRotation * 10);

        //eleven
        mElevenOClockActor.setTranslation(smallHourMarkerXTranslation, smallHourMarkerYTranslation);
        mElevenOClockActor.setRotation(oneHourRotation * 11);

    }


    private void initBigTimeMarkers() {
        //big time markers
        float bigHourMarkerYTranslation = -(mWatchScreenActor.getBitmap().getHeight() / 2f - mSixOClockActor.getBitmap().getHeight() / 2f);
        float bigHourMarkerXTranslation = -mSixOClockActor.getBitmap().getWidth() / 2f;

        //twelve
        mTwelweOClockActor.setTranslation(bigHourMarkerXTranslation, bigHourMarkerYTranslation);
        mTwelweOClockActor.setRotation(0);

        //three
        mThreeOClockActor.setTranslation(bigHourMarkerXTranslation, bigHourMarkerYTranslation);
        mThreeOClockActor.setRotation(90);

        //Six
        mSixOClockActor.setTranslation(bigHourMarkerXTranslation, bigHourMarkerYTranslation);
        mSixOClockActor.setRotation(180);

        //Nine
        mNineOClockActor.setTranslation(bigHourMarkerXTranslation, bigHourMarkerYTranslation);
        mNineOClockActor.setRotation(270);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (IActor actor : mActorsList) {
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