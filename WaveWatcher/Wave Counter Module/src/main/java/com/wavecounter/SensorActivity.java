package com.wavecounter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;

import com.wavecounter.R;

/**
 * Created by ryan on 11/17/13.
 */
public class SensorActivity extends Activity implements SensorEventListener {
    private static final String LOG_TAG = "wavecounter sensors";
    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;

    public SensorActivity() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        //Right in here is where you put code to read the current sensor values and
        //update any views you might have that are displaying the sensor information
        //You'd get accelerometer values like this:
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        float mSensorX, mSensorY, mSensorZ;
        mSensorX = event.values[0];
        mSensorY = event.values[1];
        mSensorZ = event.values[2];

        Log.i(LOG_TAG, mSensorX + "," + mSensorY + "," + mSensorZ);

//        switch (mDisplay.getRotation()) {
//            case Surface.ROTATION_0:
//                mSensorX = event.values[0];
//                mSensorY = event.values[1];
//                break;
//            case Surface.ROTATION_90:
//                mSensorX = -event.values[1];
//                mSensorY = event.values[0];
//                break;
//            case Surface.ROTATION_180:
//                mSensorX = -event.values[0];
//                mSensorY = -event.values[1];
//                break;
//            case Surface.ROTATION_270:
//                mSensorX = event.values[1];
//                mSensorY = -event.values[0];
//        }

    }
}