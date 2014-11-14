package com.yan.custom.views.watch.math;

import android.graphics.PointF;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public class WatchMathUtils {

    public static void rotatePointAroundOrigin(PointF point, PointF origin, float angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double newX = origin.x + (point.x - origin.x) * Math.cos(angleRadians) - (point.y - origin.y) * Math.sin(angleRadians);
        double newY = origin.y + (point.x - origin.x) * Math.sin(angleRadians) + (point.y - origin.y) * Math.cos(angleRadians);
        point.x = (float) newX;
        point.y = (float) newY;
    }
}
