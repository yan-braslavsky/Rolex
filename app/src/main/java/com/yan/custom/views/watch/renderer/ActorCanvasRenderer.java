package com.yan.custom.views.watch.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.yan.custom.views.watch.actors.IActor;

/**
 * Created by Yan on 11/14/2014.
 *
 * This class is used to render an {@link com.yan.custom.views.watch.actors.IActor}
 * on the {@link com.yan.custom.views.watch.WatchView}.
 */
public class ActorCanvasRenderer {

    //we are reusing the same paint
    //to draw all actors
    private final Paint mPaint;

    public ActorCanvasRenderer() {
        mPaint = new Paint();
    }

    /**
     * Takes the actor and renders it on the canvas considering
     * actor properties.
     * @param actor to draw.
     * @param canvas to draw actor on.
     */
    public void renderActorOnCanvas(IActor actor, Canvas canvas) {

        //push matrix
        canvas.save();
        {
            //we are drawing relative to origin
            float canvasOriginX = canvas.getWidth() / 2;
            float canvasOriginY = canvas.getHeight() / 2;

            //rotate matrix according to actor properties
            canvas.rotate(actor.getRotation(), canvas.getWidth() / 2, canvas.getHeight() / 2);

            //translate matrix according to actor properties
            canvas.translate(actor.getTranslation().x, actor.getTranslation().y);

            //draw the actor relative to origin
            canvas.drawBitmap(actor.getBitmap(), canvasOriginX, canvasOriginY, mPaint);
        }

        //pop matrix
        canvas.restore();
    }
}
