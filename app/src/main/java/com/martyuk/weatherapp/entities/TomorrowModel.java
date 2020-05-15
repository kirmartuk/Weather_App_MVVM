package com.martyuk.weatherapp.entities;

import java.util.List;

public class TomorrowModel {
    private String timeStamp;
    private int max;
    private int min;
    private int imageResource;
    private List<WeatherHourlyModel> hourly;
    private String description;

    public TomorrowModel() {
    }

    public TomorrowModel(String timeStamp, int max, int min, int imageResource, List<WeatherHourlyModel> hourly, String description) {
        this.timeStamp = timeStamp;
        this.max = max;
        this.min = min;
        this.imageResource = imageResource;
        this.hourly = hourly;
        this.description = description;
    }

    @Override
    public String toString() {
        return "TomorrowModel{" +
                "timeStamp='" + timeStamp + '\'' +
                ", max=" + max +
                ", min=" + min +
                ", imageResource=" + imageResource +
                ", hourly=" + hourly +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public List<WeatherHourlyModel> getHourly() {
        return hourly;
    }

    public void setHourly(List<WeatherHourlyModel> hourly) {
        this.hourly = hourly;
    }
}
