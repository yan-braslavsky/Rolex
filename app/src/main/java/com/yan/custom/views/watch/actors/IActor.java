package com.yan.custom.views.watch.actors;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.yan.custom.views.watch.physics.ICollider;

/**
 * Created by Yan on 11/14/2014.
 * This interface defines a single actor
 * that will be drawn in the {@link com.yan.custom.views.watch.WatchView}.
 */
public interface IActor {

    void setTranslation(float x, float y);

    void setCollider(ICollider collider);

    void setBitmap(Bitmap bmp);

    void setRotation(float rotation);

    float getRotation();

    PointF getTranslation();

    ICollider getCollider();

    Bitmap getBitmap();
}
