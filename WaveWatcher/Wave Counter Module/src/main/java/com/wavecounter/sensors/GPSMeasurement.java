package com.wavecounter.sensors;

/**
 * Created by ryan on 11/17/13.
 */
public class GPSMeasurement {

    long measurementTime;
    double lat;
    double lng;

    public GPSMeasurement(long measurementTime, double lat, double lng) {
        this.measurementTime = measurementTime;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPSMeasurement that = (GPSMeasurement) o;

        if (Double.compare(that.lat, lat) != 0) return false;
        if (Double.compare(that.lng, lng) != 0) return false;
        if (measurementTime != that.measurementTime) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (measurementTime ^ (measurementTime >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "GPSMeasurement{" +
                "measurementTime=" + measurementTime +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public long getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(long measurementTime) {
        this.measurementTime = measurementTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
