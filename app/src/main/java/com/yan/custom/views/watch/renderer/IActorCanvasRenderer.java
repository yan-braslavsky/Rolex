package com.yan.custom.views.watch.renderer;

import android.graphics.Canvas;

import com.yan.custom.views.watch.actors.ArrowActor;

/**
 * Created by Yan-Home on 11/14/2014.
 */
public interface IActorCanvasRenderer {

    void renderActorOnCanvas(ArrowActor actor, Canvas canvas);
}
