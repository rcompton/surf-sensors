package com.wavecounter.sensors;

/**
 * Created by ryan on 11/17/13.
 */
public class RotationVectorMeasurement {

    long measurementTime;
    double gx;
    double gy;
    double gz;

    public RotationVectorMeasurement(long measurementTime, double gx, double gy, double gz) {
        this.measurementTime = measurementTime;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
    }

    @Override
    public String toString(){
        return measurementTime + "\t" + gx + "\t" + gy + "\t" + gz;
    }

    public long getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(long measurementTime) {
        this.measurementTime = measurementTime;
    }

    public double getgx() {
        return gx;
    }

    public void setgx(double gx) {
        this.gx = gx;
    }

    public double getgy() {
        return gy;
    }

    public void setgy(double gy) {
        this.gy = gy;
    }

    public double getgz() {
        return gz;
    }

    public void setgz(double gz) {
        this.gz = gz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RotationVectorMeasurement that = (RotationVectorMeasurement) o;

        if (Double.compare(that.gx, gx) != 0) return false;
        if (Double.compare(that.gy, gy) != 0) return false;
        if (Double.compare(that.gz, gz) != 0) return false;
        if (measurementTime != that.measurementTime) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (measurementTime ^ (measurementTime >>> 32));
        temp = Double.doubleToLongBits(gx);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gy);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gz);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
