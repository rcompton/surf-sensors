package com.wavecounter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ryan on 11/17/13.
 */
public class SensorActivity extends Activity implements SensorEventListener {
    private static final String LOG_TAG = "wavecounter sensors";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        Log.i(LOG_TAG, "sensor activity onCreate");
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "sensor activity onResume");
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float ax,ay,az;
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
            Log.i(LOG_TAG, ax + "," + ay + "," + az);
        }
    }


//    public void onSensorChanged(SensorEvent event) {
//        //Right in here is where you put code to read the current sensor values and
//        //update any views you might have that are displaying the sensor information
//        //You'd get accelerometer values like this:
//        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
//            return;
//        float mSensorX, mSensorY, mSensorZ;
//        mSensorX = event.values[0];
//        mSensorY = event.values[1];
//        mSensorZ = event.values[2];
//
//        Log.i(LOG_TAG, mSensorX + "," + mSensorY + "," + mSensorZ);

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
//    }
}