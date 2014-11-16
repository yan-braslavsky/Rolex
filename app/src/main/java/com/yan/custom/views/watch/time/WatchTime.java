package com.yan.custom.views.watch.time;

/**
 * Created by Yan on 11/15/2014.
 * <p/>
 * Manages a time.
 */
public class WatchTime implements IWatchTime {

    /**
     * Will be called every time the {@link WatchTime}
     * is changed.
     */
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
