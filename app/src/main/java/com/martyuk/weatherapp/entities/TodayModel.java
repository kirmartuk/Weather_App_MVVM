package com.martyuk.weatherapp.entities;

import java.util.List;


public class TodayModel {
    String timeStamp;
    int temperature;
    int imageResource;
    int feelsLike;
    List<WeatherHourlyModel> hourly;
    String description;

    public List<WeatherHourlyModel> getHourly() {
        return hourly;
    }

    @Override
    public String toString() {
        return "TodayModel{" +
                "timeStamp='" + timeStamp + '\'' +
                ", temperature=" + temperature +
                ", imageResource=" + imageResource +
                ", feelsLike=" + feelsLike +
                ", hourly=" + hourly +
                ", description='" + description + '\'' +
                '}';
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setHourly(List<WeatherHourlyModel> hourly) {
        this.hourly = hourly;
    }

    public TodayModel(String timeStamp,
                      int temperature,
                      int imageResource,
                      int feelsLike,
                      List<WeatherHourlyModel> hourly,
                      String description) {
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.imageResource = imageResource;
        this.feelsLike = feelsLike;
        this.hourly = hourly;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TodayModel(String timeStamp, int temperature, int imageResource, List<WeatherHourlyModel> hourly) {
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.imageResource = imageResource;
        this.hourly = hourly;
    }

    public TodayModel(String timeStamp, int temperature, int imageResource) {
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.imageResource = imageResource;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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
}
