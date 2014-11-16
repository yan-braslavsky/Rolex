package com.yan.custom.views.watch.physics;

/**
 * Created by Yan on 11/14/2014.
 *
 * This interface defines a collider that
 * can be used for touch detections.
 */
public interface ICollider {
    boolean contains(float x, float y);
}
