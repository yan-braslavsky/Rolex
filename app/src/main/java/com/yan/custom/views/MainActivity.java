package com.yan.custom.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yan.custom.views.watch.WatchView;
import com.yan.custom.views.watch.time.WatchTime;

import java.util.Calendar;

/**
 * Created by Yan on 11/14/2014.
 * Here we can see a little example of how the "WatchView" can be used.
 */
public class MainActivity extends Activity {

    /**
     * The amount of seconds to add for each degree of winding wheel rotation
     */
    private static int SECONDS_TO_ADD_FOR_ONE_ROTATION_DEGREE = 1;

    //async task is used to simulate real watch functionality
    private TimeTickingTask mTimeTickingTask;
    private WatchView mWatchView;
    private TextView mTextView;

    //Sound
    private SoundPool mSoundPool;
    private int mWindingSoundStreamID;
    private int mTikTakSoundStreamID;
    private int mTickTackSoundId;
    private int mWindingSoundId;

    //watch view scale state
    private boolean mScaled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init sound pool and load some sounds
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mTickTackSoundId = mSoundPool.load(this, R.raw.tik_tak_sound, 1);
        mWindingSoundId = mSoundPool.load(this, R.raw.winding_sound, 1);

        //inflate views
        mWatchView = (WatchView) findViewById(R.id.watch_view);
        mTextView = (TextView) findViewById(R.id.textView);

        //Here we are using watch view functionality
        initWatchView();
    }

    private void initWatchView() {

        //We can set a listener that is triggered every time the time on the watch view changes.
        //The time change can be caused by setting explicitly a new time on the watch or by moving
        //the watch arrows.
        mWatchView.setWatchTimeChangeListener(new WatchTime.WatchTimeChangeListener() {
            @Override
            public void onTimeSet(int hours, int minutes, int seconds) {

                //we want to present current time on the watch in digital format on
                //the text view above
                mTextView.setText("Time  " + hours + ":" + minutes + ":" + seconds);
            }
        });


        //we will set the real time of the device
        Calendar c = Calendar.getInstance();
        //Now we setting the time.That should trigger watch change listener.
        mWatchView.setTime(c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

        //Winding wheel can be rotated , and we can track the beginning and the end of
        //the rotation.In this case we want to use a winding wheel to "charge" the watch
        //with some time to perform "ticking".
        mWatchView.setWindingWheelRotateListener(new WatchView.WindingWheelRotateListener() {

            //we will remember the rotation angle before the wheel is rotated
            float _previousAngle;

            @Override
            public void onWindingWheelRotationBegin(float currentRotationAngle) {

                //remember the angle
                _previousAngle = currentRotationAngle;

                //stop the current winding wheel sound (if it plays , otherwise this line has no effect)
                mSoundPool.stop(mWindingSoundStreamID);

                //play winding wheel sound , and remember the stream Id to cancel it afterwards
                mWindingSoundStreamID = mSoundPool.play(mWindingSoundId, 1, 1, 1, -1, 1f);
            }

            @Override
            public void onWindingWheelRotationEnd(float currentRotationAngle) {

                //we must cancel the previous async task
                //because we need to start another one.
                if (mTimeTickingTask != null) {
                    mTimeTickingTask.cancel(true);
                }

                //stop the current winding wheel sound
                mSoundPool.stop(mWindingSoundStreamID);
                mSoundPool.stop(mTikTakSoundStreamID);
                mWindingSoundStreamID = -1;

                //play ticking sound
                mTikTakSoundStreamID = mSoundPool.play(mTickTackSoundId, 1, 1, 1, -1, 1f);

                //now we are calculating how many seconds we want to add depending on the change of rotation angle of the winding wheel
                int secondsToAdd = (int) (Math.abs(currentRotationAngle - _previousAngle) * SECONDS_TO_ADD_FOR_ONE_ROTATION_DEGREE);

                //create the async task
                mTimeTickingTask = new TimeTickingTask(mWatchView, secondsToAdd);

                //we must allow parallel execution , otherwise the task will be executed only once.
                mTimeTickingTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    /**
     * That method is called when button is triggered.
     * That method is "wired" to the button through the "onClick" xml property
     * in the layout xml.
     *
     * All we want to show here is that the view can be easily animated , preserving the
     * functionality.
     */
    public void animate(View view) {

        Button btn = (Button) view;
        AnimatorSet set = new AnimatorSet();
        if (mScaled) {
            //scale up
            ObjectAnimator scaleXUp = ObjectAnimator.ofFloat(mWatchView, "scaleX", 0.5f, 1f);
            ObjectAnimator scaleYUp = ObjectAnimator.ofFloat(mWatchView, "scaleY", 0.5f, 1f);
            set.play(scaleXUp).with(scaleYUp);
            btn.setText(getString(R.string.scale_down));
        } else {
            //scale down
            ObjectAnimator scaleXDown = ObjectAnimator.ofFloat(mWatchView, "scaleX", 1f, 0.5f);
            ObjectAnimator scaleYDown = ObjectAnimator.ofFloat(mWatchView, "scaleY", 1f, 0.5f);
            set.play(scaleXDown).with(scaleYDown);
            btn.setText(getString(R.string.scale_up));
        }

        mScaled = !mScaled;
        set.setDuration(1000);
        set.start();

    }

    @Override
    protected void onStop() {
        super.onStop();

        //cancel the async task
        if (mTimeTickingTask != null) {
            mTimeTickingTask.cancel(true);
        }

        //stop any playing sounds
        mSoundPool.stop(mWindingSoundStreamID);
        mSoundPool.stop(mTikTakSoundStreamID);
    }

    /**
     * This async task is used to move the seconds arrow on the watch every second.
     */
    private class TimeTickingTask extends AsyncTask<Void, Void, Void> {

        private int _tickingDurationInSeconds;
        private WatchView _watchView;

        private TimeTickingTask(WatchView watchView, int tickingDurationInSeconds) {
            _tickingDurationInSeconds = tickingDurationInSeconds;
            _watchView = watchView;
        }

        @Override
        protected Void doInBackground(Void... params) {

            while (_tickingDurationInSeconds > 0) {
                try {
                    Thread.sleep(1000);
                    _tickingDurationInSeconds--;
                    publishProgress();
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            //update seconds
            //that will also trigger the time change listener that
            //we have set before
            _watchView.setSeconds(_watchView.getTime().getSeconds() + 1);
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            //when the task is done we want the ticking to stop
            mSoundPool.stop(mTikTakSoundStreamID);
            mTikTakSoundStreamID = -1;
        }
    }
}
