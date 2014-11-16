package com.yan.custom.views.watch.physics;

import android.graphics.PointF;

/**
 * Created by Yan on 11/14/2014.
 * Implementer of {@link com.yan.custom.views.watch.physics.ICollider}
 * that is represented as a ring area that will be checked for collisions.
 */
public class Ring2DCollider implements ICollider {

    PointF mCenter;
    float mInnerRadius;
    float mOuterRadius;

    public Ring2DCollider(PointF center, float innerRadius, float outerRadius) {
        mCenter = center;
        mInnerRadius = innerRadius;
        mOuterRadius = outerRadius;
    }

    @Override
    public boolean contains(float x, float y) {
        //offset to the center point
        x -= mCenter.x;
        y -= mCenter.y;
        double touchRadius = Math.sqrt((x * x) + (y * y));
        return (touchRadius > mInnerRadius) && (touchRadius < mOuterRadius);
    }
}
