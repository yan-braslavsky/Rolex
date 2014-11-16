package com.yan.custom.views.watch.math;

import android.graphics.PointF;

/**
 * Created by Yan on 11/14/2014.
 *
 * Bunch of useful math functions that not included in standard math library.
 */
public class WatchMathUtils {

    /**
     * Given an origin point and degrees of rotation , this method
     * will rotate the point by the given degrees around given origin.
     * @param point to be rotated.
     * @param origin to rotate point around.
     * @param angleDegrees amount of rotation degrees
     */
    public static void rotatePointAroundOrigin(PointF point, PointF origin, float angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double newX = origin.x + (point.x - origin.x) * Math.cos(angleRadians) - (point.y - origin.y) * Math.sin(angleRadians);
        double newY = origin.y + (point.x - origin.x) * Math.sin(angleRadians) + (point.y - origin.y) * Math.cos(angleRadians);
        point.x = (float) newX;
        point.y = (float) newY;
    }
}
