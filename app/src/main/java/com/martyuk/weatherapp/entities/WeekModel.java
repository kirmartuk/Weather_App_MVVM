package com.martyuk.weatherapp.entities;

import com.martyuk.weatherapp.ui.main.network.Daily;

import java.util.List;

public class WeekModel {
    private String timeStamp;
    private List<Daily> daily;

    @Override
    public String toString() {
        return "WeekModel{" +
                "timeStamp='" + timeStamp + '\'' +
                ", daily=" + daily +
                '}';
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public WeekModel(String timeStamp, List<Daily> daily) {
        this.timeStamp = timeStamp;
        this.daily = daily;
    }
}
