package com.yan.custom.views.watch.physics;

import android.graphics.RectF;

/**
 * Created by Yan on 11/14/2014.
 */
public class Box2DCollider implements ICollider {

    private RectF mBoundingRectangle;

    public Box2DCollider() {
        this(0, 0, 0, 0);
    }

    public Box2DCollider(float left, float top, float right, float bottom) {
        mBoundingRectangle = new RectF();
        setBounds(left, top, right, bottom);
    }

    public RectF getBoundingRectangle() {
        return mBoundingRectangle;
    }

    public void setBounds(float left, float top, float right, float bottom) {
        mBoundingRectangle.left = left;
        mBoundingRectangle.top = top;
        mBoundingRectangle.right = right;
        mBoundingRectangle.bottom = bottom;
    }

    @Override
    public boolean contains(float x, float y) {
        return mBoundingRectangle.contains(x,y);
    }
}
