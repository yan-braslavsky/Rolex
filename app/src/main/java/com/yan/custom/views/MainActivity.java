package com.yan.custom.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yan.custom.views.watch.WatchView;
import com.yan.custom.views.watch.time.WatchTime;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inflate views
        final WatchView watchView = (WatchView) findViewById(R.id.watch_view);
        final TextView textView = (TextView) findViewById(R.id.textView);

        //set time change listener
        watchView.setWatchTimeChangeListener(new WatchTime.WatchTimeChangeListener() {
            @Override
            public void onTimeSet(int hours, int minutes, int seconds) {
                textView.setText("Time  " + hours + ":" + minutes + ":" + seconds);
            }
        });

        //set time
        watchView.setTime(2, 15, 30);

        watchView.setWindingWheelRotateListener(new WatchView.WindingWheelRotateListener() {
            @Override
            public void onWindingWheelRotationBegin(float currentRotationAngle) {
                //TODO : remember the angle
            }

            @Override
            public void onWindingWheelRotationEnd(float currentRotationAngle) {
                //TODO : depending on rotation angle add ticking time
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
