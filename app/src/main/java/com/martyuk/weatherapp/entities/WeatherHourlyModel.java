package com.martyuk.weatherapp.entities;

public class WeatherHourlyModel {
    int temperature;
    int imageResource;
    long time;
    String timeZone;

    @Override
    public String toString() {
        return "WeatherHourlyModel{" +
                "temperature=" + temperature +
                ", imageResource=" + imageResource +
                ", time=" + time +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public WeatherHourlyModel() {
    }

    public WeatherHourlyModel(int temperature, int imageResource, long time, String timeZone) {
        this.temperature = temperature;
        this.imageResource = imageResource;
        this.time = time;
        this.timeZone = timeZone;
    }
}
