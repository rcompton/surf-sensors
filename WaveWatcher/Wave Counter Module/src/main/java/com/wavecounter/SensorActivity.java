package com.wavecounter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wavecounter.GPS.GPSManager;
import com.wavecounter.sensors.GPSMeasurement;
import com.wavecounter.sensors.GyroscopeMeasurement;
import com.wavecounter.sensors.LinearAccelerometerMeasurement;
import com.wavecounter.sensors.RotationVectorMeasurement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 11/17/13.
 */
public class SensorActivity extends Activity implements SensorEventListener, LocationListener {
    private static final String LOG_TAG = "wavecounter sensors";

    private SensorManager mSensorManager;
    private Sensor mLinearAccelerometer;
    private Sensor mGyroscope;
    private Sensor mRotationVector;

    private String fnameTimeStamp;

    private List<LinearAccelerometerMeasurement> linearAccelerometerMeasurements;
    private List<RotationVectorMeasurement> rotationVectorMeasurements;
    private List<GyroscopeMeasurement> gyroscopeMeasurements;

    private List<GPSMeasurement> gpsMeasurements;

    /**
     * rotating device calls oncreate?
     */
    public SensorActivity(){
        fnameTimeStamp = Long.toString(System.currentTimeMillis());
        linearAccelerometerMeasurements = new ArrayList<LinearAccelerometerMeasurement>();
        rotationVectorMeasurements = new ArrayList<RotationVectorMeasurement>();
        gyroscopeMeasurements = new ArrayList<GyroscopeMeasurement>();
        gpsMeasurements = new ArrayList<GPSMeasurement>();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        Log.i(LOG_TAG, "sensor activity onCreate");

        //sensor manager
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //register accelerometer
        mLinearAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mLinearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        //register gyroscope
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);

        //register rotation vector
        mRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mSensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);

        //GPS, check that it's enabled.
        GPSManager gpsManager = new GPSManager(SensorActivity.this);
        gpsManager.start();


    }

    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "sensor activity onResume");
        mSensorManager.registerListener(this, mLinearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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

        Log.i(LOG_TAG, Integer.toString(linearAccelerometerMeasurements.size()) );
        try{
            //write acceleration data
            File linearAccelFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "linear_accel_data_"+fnameTimeStamp+".tsv");
            Toast.makeText(getApplicationContext(), linearAccelFile.toString(), Toast.LENGTH_LONG).show();
            Log.i(LOG_TAG, linearAccelFile.toString() );
            BufferedWriter bw = new BufferedWriter(new FileWriter(linearAccelFile));
            for(LinearAccelerometerMeasurement am : linearAccelerometerMeasurements){
                bw.write(am.toString() + "\n");
            }
            bw.close();

            //write gyro data
            File gyroFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "gyroscope_data_"+fnameTimeStamp+".tsv");
            Toast.makeText(getApplicationContext(), gyroFile.toString(), Toast.LENGTH_LONG).show();
            Log.i(LOG_TAG, gyroFile.toString() );
            bw = new BufferedWriter(new FileWriter(gyroFile));
            for(GyroscopeMeasurement gm : gyroscopeMeasurements){
                bw.write(gm.toString() + "\n");
            }
            bw.close();

            //write rotation data
            File rvFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "rotation_vector_data_"+fnameTimeStamp+".tsv");
            Toast.makeText(getApplicationContext(), rvFile.toString(), Toast.LENGTH_LONG).show();
            Log.i(LOG_TAG, rvFile.toString() );
            bw = new BufferedWriter(new FileWriter(rvFile));
            for(RotationVectorMeasurement rvm : rotationVectorMeasurements){
                bw.write(rvm.toString() + "\n");
            }
            bw.close();

            //write GPS data
            File gpsFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "gps_data_"+fnameTimeStamp+".tsv");
            Toast.makeText(getApplicationContext(), rvFile.toString(), Toast.LENGTH_LONG).show();
            Log.i(LOG_TAG, rvFile.toString() );
            bw = new BufferedWriter(new FileWriter(rvFile));
            for(GPSMeasurement gpsm : gpsMeasurements){
                bw.write(gpsm.toString() + "\n");
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

        if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
            float ax,ay,az;
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];
            //Log.i(LOG_TAG, ax + "," + ay + "," + az);
            linearAccelerometerMeasurements.add(new LinearAccelerometerMeasurement(currentTime, ax, ay, az));
        }

        if (event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            float gx,gy,gz;
            gx=event.values[0];
            gy=event.values[1];
            gz=event.values[2];
            //Log.i(LOG_TAG, ax + "," + ay + "," + az);
            gyroscopeMeasurements.add(new GyroscopeMeasurement(currentTime, gx, gy, gz));
        }

        if (event.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR){
            float gx,gy,gz;
            gx=event.values[0];
            gy=event.values[1];
            gz=event.values[2];
            //Log.i(LOG_TAG, ax + "," + ay + "," + az);
            rotationVectorMeasurements.add(new RotationVectorMeasurement(currentTime, gx, gy, gz));
        }


    }

    /**
     * GPS location changed
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        long currentTime = System.currentTimeMillis();

        Log.i(LOG_TAG, "LAT"+ String.valueOf(location.getLatitude()));
        Log.i(LOG_TAG,"LONG"+ String.valueOf(location.getLongitude()));
        Log.i(LOG_TAG,"ACCURACY"+ String.valueOf(location.getAccuracy() + " m"));
        Log.i(LOG_TAG,"PROVIDER"+ String.valueOf(location.getProvider()));
        Log.i(LOG_TAG,"SPEED"+ String.valueOf(location.getSpeed() + " m/s"));
        Log.i(LOG_TAG,"ALTITUDE"+ String.valueOf(location.getAltitude()));
        Log.i(LOG_TAG,"BEARING"+ String.valueOf(location.getBearing() + " degrees east of true north"));

        if (location != null) {
            gpsMeasurements.add(new GPSMeasurement(currentTime, location.getLatitude(), location.getLongitude()));
        }

    }

    //GPSManager handles this
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    //GPSManager handles this
    @Override
    public void onProviderEnabled(String s) {
    }
    //GPSManager handles this
    @Override
    public void onProviderDisabled(String s) {
    }
}