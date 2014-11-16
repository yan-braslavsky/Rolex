package com.yan.custom.views.watch.renderer;

import android.graphics.Canvas;

import com.yan.custom.views.watch.actors.IActor;

/**
 * Created by Yan on 11/14/2014.
 */
public interface IActorCanvasRenderer {

    void renderActorOnCanvas(IActor actor, Canvas canvas);
}
