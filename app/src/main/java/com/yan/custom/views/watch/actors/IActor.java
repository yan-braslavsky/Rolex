package com.yan.custom.views.watch.actors;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public interface IActor {
    Bitmap getBitmap();

    float getRotation();

    void setTranslation(float x,float y);
    PointF getTranslation();

    void setRotation(float rotation);
}
