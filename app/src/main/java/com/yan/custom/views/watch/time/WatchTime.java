package com.yan.custom.views.watch.time;

/**
 * Created by Yan-Home on 11/15/2014.
 */
public class WatchTime implements IWatchTime {

    public interface WatchTimeChangeListener {
        void onTimeSet(int hours, int minutes, int seconds);
    }

    private int mHours;
    private int mMinutes;
    private int mSeconds;
    private WatchTimeChangeListener mWatchTimeChangeListener;

    public WatchTime(int hours, int minutes, int seconds) {
        mHours = hours;
        mMinutes = minutes;
        mSeconds = seconds;
    }

    @Override
    public int getHours() {
        return mHours;
    }

    public void setHours(int hours) {
        mHours = hours;
        if (mWatchTimeChangeListener != null)
            mWatchTimeChangeListener.onTimeSet(mHours, mMinutes, mSeconds);
    }

    @Override
    public int getMinutes() {
        return mMinutes;
    }

    public void setMinutes(int minutes) {
        mMinutes = minutes;
        if (mWatchTimeChangeListener != null)
            mWatchTimeChangeListener.onTimeSet(mHours, mMinutes, mSeconds);
    }

    @Override
    public int getSeconds() {
        return mSeconds;
    }

    public void setSeconds(int seconds) {
        mSeconds = seconds;
        if (mWatchTimeChangeListener != null)
            mWatchTimeChangeListener.onTimeSet(mHours, mMinutes, mSeconds);
    }

    public void setWatchTimeChangeListener(WatchTimeChangeListener watchTimeChangeListener) {
        mWatchTimeChangeListener = watchTimeChangeListener;
    }
}
