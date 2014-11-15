package com.yan.custom.views;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.yan.custom.views.watch.WatchView;
import com.yan.custom.views.watch.time.WatchTime;


public class MainActivity extends Activity {

    private static int SECONDS_TO_ADD_FOR_ONE_ROTATION_DEGREE = 1;
    private int mTickTackSoundId;
    private int mWindingSoundId;
    private TimeTickingTask mTimeTickingTask;
    private WatchView mWatchView;
    private TextView mTextView;
    private SoundPool mSoundPool;
    private int mWindingSoundStreamID;
    private int mTikTakSoundStreamID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init sound pool
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.i("yan", "sound with id loaded " + sampleId + " with status " + status);
            }
        });
        mTickTackSoundId = mSoundPool.load(this, R.raw.tik_tak_sound, 1);
        mWindingSoundId = mSoundPool.load(this, R.raw.winding_sound, 1);

        //inflate views
        mWatchView = (WatchView) findViewById(R.id.watch_view);
        mTextView = (TextView) findViewById(R.id.textView);

        //set time change listener
        mWatchView.setWatchTimeChangeListener(new WatchTime.WatchTimeChangeListener() {
            @Override
            public void onTimeSet(int hours, int minutes, int seconds) {
                mTextView.setText("Time  " + hours + ":" + minutes + ":" + seconds);
            }
        });

        //set time
        mWatchView.setTime(2, 15, 30);

        mWatchView.setWindingWheelRotateListener(new WatchView.WindingWheelRotateListener() {
            //we will remember the rotation angle before the wheel is rotated
            float _previousAngle;

            @Override
            public void onWindingWheelRotationBegin(float currentRotationAngle) {
                _previousAngle = currentRotationAngle;

                //stop the current winding_sound sound
                mSoundPool.stop(mWindingSoundStreamID);

                //play winding_sound sound
                mWindingSoundStreamID = mSoundPool.play(mWindingSoundId, 1, 1, 1, -1, 1f);
            }

            @Override
            public void onWindingWheelRotationEnd(float currentRotationAngle) {
                if (mTimeTickingTask != null) {
                    mTimeTickingTask.cancel(true);
                }

                //stop the current winding_sound sound
                mSoundPool.stop(mWindingSoundStreamID);
                mSoundPool.stop(mTikTakSoundStreamID);
                mWindingSoundStreamID = -1;
                mTikTakSoundStreamID = mWindingSoundStreamID = mSoundPool.play(mTickTackSoundId, 1, 1, 1, -1, 1f);

                int secondsToAdd = (int) (Math.abs(currentRotationAngle - _previousAngle) * SECONDS_TO_ADD_FOR_ONE_ROTATION_DEGREE);
                mTimeTickingTask = new TimeTickingTask(mWatchView, secondsToAdd);
                mTimeTickingTask.execute();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //cancel the async task
        if (mTimeTickingTask != null) {
            mTimeTickingTask.cancel(true);
        }

        mSoundPool.stop(mWindingSoundStreamID);
        mSoundPool.stop(mTikTakSoundStreamID);
    }

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
            _watchView.setSeconds(_watchView.getTime().getSeconds() + 1);
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);

            mSoundPool.stop(mTikTakSoundStreamID);
            mTikTakSoundStreamID = -1;
        }
    }
}
