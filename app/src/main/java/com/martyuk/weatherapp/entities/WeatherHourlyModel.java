package com.martyuk.weatherapp.entities;

public class WeatherHourlyModel {
    int temperature;
    int imageResource;
    String time;

    @Override
    public String toString() {
        return "TodayWeatherHourlyModel{" +
                "temperature=" + temperature +
                ", imageResource=" + imageResource +
                ", time='" + time + '\'' +
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public WeatherHourlyModel(int temperature, int imageResource, String time) {
        this.temperature = temperature;
        this.imageResource = imageResource;
        this.time = time;
    }
}
