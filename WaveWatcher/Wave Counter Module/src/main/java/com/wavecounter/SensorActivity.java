package com.wavecounter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wavecounter.sensors.AccelerometerMeasurement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 11/17/13.
 */
public class SensorActivity extends Activity implements SensorEventListener {
    private static final String LOG_TAG = "wavecounter sensors";

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;


    private String fnameTimeStamp;

    private List<AccelerometerMeasurement> accelerometerMeasurements;

    /**
     * rotating device calls oncreate?
     */
    public SensorActivity(){
        fnameTimeStamp = Long.toString(System.currentTimeMillis());
        accelerometerMeasurements = new ArrayList<AccelerometerMeasurement>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        Log.i(LOG_TAG, "sensor activity onCreate");

        //sensor manager
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //register accelerometer
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        //register gyroscope
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);

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

    /**
     * user pressed save button, write all sensor data to disk and exit.
     * @param view
     */
    public void saveButton(View view){

        Log.i(LOG_TAG, Integer.toString(accelerometerMeasurements.size()) );
        try{
            File accelFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "accel_data_"+fnameTimeStamp+".tsv");

            Toast.makeText(getApplicationContext(), accelFile.toString(), Toast.LENGTH_LONG).show();

            Log.i(LOG_TAG, accelFile.toString() );

            BufferedWriter bw = new BufferedWriter(new FileWriter(accelFile));
            for(AccelerometerMeasurement am : accelerometerMeasurements){
                bw.write(am.toString() + "\n");
            }
            bw.close();

        }catch (Exception e){
            Log.e(LOG_TAG,"io problem", e);
        }

        finish();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        long currentTime = System.currentTimeMillis();

        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float ax,ay,az;
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
            //Log.i(LOG_TAG, ax + "," + ay + "," + az);
            accelerometerMeasurements.add(new AccelerometerMeasurement(currentTime, ax, ay, az));
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