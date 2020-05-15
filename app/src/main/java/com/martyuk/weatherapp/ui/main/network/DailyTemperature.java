package com.martyuk.weatherapp.ui.main.network;

public class DailyTemperature {
    float max;
    float min;

    @Override
    public String toString() {
        return "DailyTemperature{" +
                "max=" + max +
                ", min=" + min +
                '}';
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public DailyTemperature() {
    }

    public DailyTemperature(float max, float min) {
        this.max = max;
        this.min = min;
    }
}
