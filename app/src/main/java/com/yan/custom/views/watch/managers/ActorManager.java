package com.yan.custom.views.watch.managers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.yan.custom.views.R;
import com.yan.custom.views.watch.actors.BaseActor;
import com.yan.custom.views.watch.actors.IActor;
import com.yan.custom.views.watch.physics.Box2DCollider;
import com.yan.custom.views.watch.physics.Ring2DCollider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan on 11/15/2014.
 */
public class ActorManager {

    private static int TARGET_SIZE = 800;

    private List<IActor> mTouchableActors;
    private PointF mViewCenterPoint;
    private ArrayList<IActor> mActorsDisplayList;
    private Resources mResources;

    //Background actors
    private IActor mWatchStrapActor;
    private IActor mWatchScreenActor;

    //touchable actors
    private IActor mHoursArrowActor;
    private IActor mMinutesArrowActor;
    private IActor mSecondsArrowActor;
    private IActor mWindingWheel;

    //Big time actors
    private IActor mTwelveOClockActor;
    private IActor mThreeOClockActor;
    private IActor mSixOClockActor;
    private IActor mNineOClockActor;

    //Small time actors
    private IActor mOneOClockActor;
    private IActor mTwoOClockActor;
    private IActor mFourOClockActor;
    private IActor mFiveOClockActor;
    private IActor mSevenOClockActor;
    private IActor mEightOClockActor;
    private IActor mTenOClockActor;
    private IActor mElevenOClockActor;


    public ActorManager(Resources resources) {
        mActorsDisplayList = new ArrayList<IActor>();
        mTouchableActors = new ArrayList<IActor>();
        mViewCenterPoint = new PointF();
        mResources = resources;

        //initialize the actors
        createActors();
        fillTouchableActorsList();
        addActorsToDisplayList();
    }

    private void createActors() {

        mWatchStrapActor = new BaseActor(createBitmap(R.drawable.strap));
        mWindingWheel = new BaseActor(createBitmap(R.drawable.wheel));
        mWatchScreenActor = new BaseActor(createBitmap(R.drawable.screen));

        //arrows
        mHoursArrowActor = new BaseActor(createBitmap(R.drawable.hour));
        mMinutesArrowActor = new BaseActor(createBitmap(R.drawable.min));
        mSecondsArrowActor = new BaseActor(createBitmap(R.drawable.sek));

        //big times lines
        //we are reusing the same bitmap for all the actors to save memory
        Bitmap smallLineBmp = createBitmap(R.drawable.b_line);
        mTwelveOClockActor = new BaseActor(smallLineBmp);
        mThreeOClockActor = new BaseActor(smallLineBmp);
        mSixOClockActor = new BaseActor(smallLineBmp);
        mNineOClockActor = new BaseActor(smallLineBmp);

        //small times lines
        //we are reusing the same bitmap for all the actors to save memory
        Bitmap bigLineBmp = createBitmap(R.drawable.s_line);
        mOneOClockActor = new BaseActor(bigLineBmp);
        mTwoOClockActor = new BaseActor(bigLineBmp);
        mFourOClockActor = new BaseActor(bigLineBmp);
        mFiveOClockActor = new BaseActor(bigLineBmp);
        mSevenOClockActor = new BaseActor(bigLineBmp);
        mEightOClockActor = new BaseActor(bigLineBmp);
        mTenOClockActor = new BaseActor(bigLineBmp);
        mElevenOClockActor = new BaseActor(bigLineBmp);
    }

    private Bitmap createBitmap(int resId) {
        return BitmapFactory.decodeResource(mResources, resId);
    }

    private Bitmap rescaleBitmapToFitDimensions(Bitmap srcBitmap) {
        float viewWidth = mViewCenterPoint.x * 2;
        float viewHeight = mViewCenterPoint.y * 2;
        float scaleX = viewWidth / TARGET_SIZE;
        float scaleY = viewHeight / TARGET_SIZE;
        int dstWidth = (int) (srcBitmap.getWidth() * scaleX);
        int dstHeight = (int) (srcBitmap.getHeight() * scaleY);
        return Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, false);
    }

    private void fillTouchableActorsList() {
        mTouchableActors.add(mWindingWheel);
        mTouchableActors.add(mSecondsArrowActor);
        mTouchableActors.add(mMinutesArrowActor);
        mTouchableActors.add(mHoursArrowActor);
    }

    /**
     * All added actors to display list will be drawn in the view.
     * Order of adding is matters.The first actor added will be
     * drawn beneath all other actors.The last one added will
     * be above. It is called painters algorithm.
     * http://en.wikipedia.org/wiki/Painter's_algorithm
     */
    private void addActorsToDisplayList() {
        //The last one added will be the first one visible
        mActorsDisplayList.add(mWatchStrapActor);
        mActorsDisplayList.add(mWindingWheel);
        mActorsDisplayList.add(mWatchScreenActor);

        //big time markers
        mActorsDisplayList.add(mTwelveOClockActor);
        mActorsDisplayList.add(mThreeOClockActor);
        mActorsDisplayList.add(mSixOClockActor);
        mActorsDisplayList.add(mNineOClockActor);

        //small time markers
        mActorsDisplayList.add(mOneOClockActor);
        mActorsDisplayList.add(mTwoOClockActor);
        mActorsDisplayList.add(mFourOClockActor);
        mActorsDisplayList.add(mFiveOClockActor);
        mActorsDisplayList.add(mSevenOClockActor);
        mActorsDisplayList.add(mEightOClockActor);
        mActorsDisplayList.add(mTenOClockActor);
        mActorsDisplayList.add(mElevenOClockActor);

        //arrows
        mActorsDisplayList.add(mHoursArrowActor);
        mActorsDisplayList.add(mMinutesArrowActor);
        mActorsDisplayList.add(mSecondsArrowActor);

    }

    public void setViewSize(float w, float h) {
        mViewCenterPoint.x = w / 2;
        mViewCenterPoint.y = h / 2;
    }

    public void recalculateActorsMetrics() {

        //rescale all bitmaps
        for (IActor iActor : mActorsDisplayList) {
            iActor.setBitmap(rescaleBitmapToFitDimensions(iActor.getBitmap()));
        }

        //Hours arrow
        mHoursArrowActor.setTranslation(-mHoursArrowActor.getBitmap().getWidth() / 2, -(mHoursArrowActor.getBitmap().getHeight() * 0.9f));
        float left = mViewCenterPoint.x + mHoursArrowActor.getTranslation().x;
        float top = mViewCenterPoint.y + mHoursArrowActor.getTranslation().y;
        mHoursArrowActor.setCollider(new Box2DCollider(left, top, left + mHoursArrowActor.getBitmap().getWidth(), top + mHoursArrowActor.getBitmap().getHeight()));

        //Minutes arrow
        mMinutesArrowActor.setTranslation(-mMinutesArrowActor.getBitmap().getWidth() / 2, -(mMinutesArrowActor.getBitmap().getHeight() * 0.9f));
        left = mViewCenterPoint.x + mMinutesArrowActor.getTranslation().x;
        top = mViewCenterPoint.y + mMinutesArrowActor.getTranslation().y;
        mMinutesArrowActor.setCollider(new Box2DCollider(left, top, left + mMinutesArrowActor.getBitmap().getWidth(), top + mMinutesArrowActor.getBitmap().getHeight()));

        //Seconds arrow
        mSecondsArrowActor.setTranslation(-mSecondsArrowActor.getBitmap().getWidth() / 2, -(mSecondsArrowActor.getBitmap().getHeight() * 0.9f));
        left = mViewCenterPoint.x + mSecondsArrowActor.getTranslation().x;
        top = mViewCenterPoint.y + mSecondsArrowActor.getTranslation().y;
        mSecondsArrowActor.setCollider(new Box2DCollider(left, top, left + mSecondsArrowActor.getBitmap().getWidth(), top + mSecondsArrowActor.getBitmap().getHeight()));


        //strap
        mWatchStrapActor.setTranslation(-mWatchStrapActor.getBitmap().getWidth() / 2, -mWatchStrapActor.getBitmap().getHeight() / 2);

        //wheel
        mWindingWheel.setTranslation(-mWindingWheel.getBitmap().getWidth() / 2, -mWindingWheel.getBitmap().getHeight() / 2);
        float ringWidth = (mViewCenterPoint.x * 2) * 0.1f;
        float windingWheelHalfWidth = mWindingWheel.getBitmap().getWidth() / 2;
        mWindingWheel.setCollider(new Ring2DCollider(mViewCenterPoint, windingWheelHalfWidth - ringWidth, windingWheelHalfWidth /*+ ringWidth*/));

        //screen
        mWatchScreenActor.setTranslation(-mWatchScreenActor.getBitmap().getWidth() / 2, -mWatchScreenActor.getBitmap().getHeight() / 2);

        initBigTimeMarkers();
        initSmallTimeMarkers();
    }

    private void initSmallTimeMarkers() {
        //big time markers
        float smallHourMarkerYTranslation = -(mWatchScreenActor.getBitmap().getHeight() / 2f - (mOneOClockActor.getBitmap().getHeight() * 1.2f));
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
        float bigHourMarkerYTranslation = -(mWatchScreenActor.getBitmap().getHeight() / 2f - (mSixOClockActor.getBitmap().getHeight() * 1.2f) / 2f);
        float bigHourMarkerXTranslation = -mSixOClockActor.getBitmap().getWidth() / 2f;

        //twelve
        mTwelveOClockActor.setTranslation(bigHourMarkerXTranslation, bigHourMarkerYTranslation);
        mTwelveOClockActor.setRotation(0);

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

    public ArrayList<IActor> getActorsDisplayList() {
        return mActorsDisplayList;
    }

    public List<IActor> getTouchableActors() {
        return mTouchableActors;
    }

    public IActor getHoursArrowActor() {
        return mHoursArrowActor;
    }

    public IActor getMinutesArrowActor() {
        return mMinutesArrowActor;
    }

    public IActor getSecondsArrowActor() {
        return mSecondsArrowActor;
    }

    public IActor getWindingWheel() {
        return mWindingWheel;
    }
}