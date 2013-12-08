package com.wavecounter.GPS;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GPSListener implements LocationListener {

    private static  String LOG_TAG = "GPS";
    private Activity activity;
    private LocationManager lm;
    private int numberOfUpdates;

    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public static final int MAX_NUMBER_OF_UPDATES = 10;

    public GPSListener(Activity activity, LocationManager lm) {
        this.activity = activity;
        this.lm = lm;
    }

    @Override
    public void onLocationChanged(Location loc) {
        if (numberOfUpdates < MAX_NUMBER_OF_UPDATES) {
            numberOfUpdates++;

            Log.w(LOG_TAG, "LAT"+ String.valueOf(loc.getLatitude()));
            Log.w(LOG_TAG,"LONG"+ String.valueOf(loc.getLongitude()));
            Log.w(LOG_TAG,"ACCURACY"+ String.valueOf(loc.getAccuracy() + " m"));
            Log.w(LOG_TAG,"PROVIDER"+ String.valueOf(loc.getProvider()));
            Log.w(LOG_TAG,"SPEED"+ String.valueOf(loc.getSpeed() + " m/s"));
            Log.w(LOG_TAG,"ALTITUDE"+ String.valueOf(loc.getAltitude()));
            Log.w(LOG_TAG,"BEARING"+ String.valueOf(loc.getBearing() + " degrees east of true north"));

            String message;

            if (loc != null) {
                message = "Current location is:  Latitude = "
                        + loc.getLatitude() + ", Longitude = "
                        + loc.getLongitude();
                // lm.removeUpdates(this);
                lat = loc.getLatitude();
                lng = loc.getLongitude();

            } else
                message = "Location null";

            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        } else {
            lm.removeUpdates(this);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(activity, "Gps Disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(activity, "Gps Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}