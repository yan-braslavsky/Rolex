package com.yan.custom.views.watch.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.yan.custom.views.watch.actors.IActor;

/**
 * Created by Yan on 11/14/2014.
 */
public class ActorCanvasRenderer implements IActorCanvasRenderer {


    private final Paint mPaint;

    public ActorCanvasRenderer() {
        mPaint = new Paint();
    }

    @Override
    public void renderActorOnCanvas(IActor actor, Canvas canvas) {

        //push matrix
        canvas.save();
        {
            float canvasOriginX = canvas.getWidth() / 2;
            float canvasOriginY = canvas.getHeight() / 2;

            canvas.rotate(actor.getRotation(), canvas.getWidth() / 2, canvas.getHeight() / 2);
            canvas.translate(actor.getTranslation().x, actor.getTranslation().y);

            ///TODO :add scale
            //canvas.scale(0.5f, 0.5f);

            canvas.drawBitmap(actor.getBitmap(), canvasOriginX, canvasOriginY, mPaint);
        }

        //pop matrix
        canvas.restore();
    }
}
