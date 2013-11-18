package com.wavecounter.sensors;

/**
 * Created by ryan on 11/17/13.
 */
public class LinearAccelerometerMeasurement {

    long measurementTime;
    double ax;
    double ay;
    double az;

    public LinearAccelerometerMeasurement(long measurementTime, double ax, double ay, double az) {
        this.measurementTime = measurementTime;
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    @Override
    public String toString(){
        return measurementTime + "\t" + ax + "\t" + ay + "\t" + az;
    }

    public long getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(long measurementTime) {
        this.measurementTime = measurementTime;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public double getAz() {
        return az;
    }

    public void setAz(double az) {
        this.az = az;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinearAccelerometerMeasurement that = (LinearAccelerometerMeasurement) o;

        if (Double.compare(that.ax, ax) != 0) return false;
        if (Double.compare(that.ay, ay) != 0) return false;
        if (Double.compare(that.az, az) != 0) return false;
        if (measurementTime != that.measurementTime) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (measurementTime ^ (measurementTime >>> 32));
        temp = Double.doubleToLongBits(ax);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(az);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
