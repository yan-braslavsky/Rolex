package com.yan.custom.views.watch.actors;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class ArrowActor implements IActor {

    private Bitmap mBitmap;
    private float mRotation;
    private PointF mTranslation;


    public ArrowActor(Bitmap bmp) {
        mBitmap = bmp;
        mRotation = 0;
        mTranslation = new PointF(0, 0);

        //TODO : remove
        setTranslation(-mBitmap.getWidth() / 2, -mBitmap.getHeight());
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public float getRotation() {
        return mRotation;
    }

    @Override
    public void setTranslation(float x, float y) {
        mTranslation.x = x;
        mTranslation.y = y;
    }

    @Override
    public PointF getTranslation() {
        return mTranslation;
    }


    @Override
    public void setRotation(float rotation) {
        mRotation = rotation;
    }
}
