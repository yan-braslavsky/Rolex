package com.yan.custom.views.watch.actors;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.yan.custom.views.watch.physics.ICollider;

/**
 * Created by Yan on 11/14/2014.
 */
public class BaseActor implements IActor {

    private Bitmap mBitmap;
    private float mRotation;
    private PointF mTranslation;
    private ICollider mCollider;

    public BaseActor(Bitmap bmp) {
        mBitmap = bmp;
        mRotation = 0;
        mTranslation = new PointF(0, 0);
    }

    @Override
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public void setBitmap(Bitmap bmp) {
        mBitmap = bmp;
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
    public ICollider getCollider() {
        return mCollider;
    }

    @Override
    public void setCollider(ICollider collider) {
        mCollider = collider;
    }

}
