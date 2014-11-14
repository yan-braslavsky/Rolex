package com.yan.custom.views.watch.actors;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class BaseActor implements IActor {

    private Bitmap mBitmap;
    private float mRotation;
    private PointF mTranslation;
    private RectF mBoundingRectangle;


    public BaseActor(Bitmap bmp) {
        mBitmap = bmp;
        mRotation = 0;
        mTranslation = new PointF(0, 0);
        mBoundingRectangle = new RectF();
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

    @Override
    public RectF getBoundingRectangle() {
        return mBoundingRectangle;
    }

    @Override
    public void setBounds( float left,float top, float right, float bottom) {
        mBoundingRectangle.left = left;
        mBoundingRectangle.top = top;
        mBoundingRectangle.right = right;
        mBoundingRectangle.bottom = bottom;
    }
}
