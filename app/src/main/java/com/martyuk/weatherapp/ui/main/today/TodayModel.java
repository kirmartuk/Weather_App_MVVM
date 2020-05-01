package com.martyuk.weatherapp.ui.main.today;

public class TodayModel {
    String timeStamp;
    int temperature;
    int imageResource;

    @Override
    public String toString() {
        return "TodayModel{" +
                "timeStamp='" + timeStamp + '\'' +
                ", temperature=" + temperature +
                ", image=" + imageResource +
                '}';
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
