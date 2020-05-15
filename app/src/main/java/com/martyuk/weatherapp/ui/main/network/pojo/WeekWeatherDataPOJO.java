package com.martyuk.weatherapp.ui.main.network.pojo;

import com.martyuk.weatherapp.ui.main.network.Daily;
import com.martyuk.weatherapp.ui.main.network.WeatherData;

import java.util.List;

public class WeekWeatherDataPOJO {
    private float lat;
    private float lon;
    private String timezone;
    private List<Daily> daily;

    public WeekWeatherDataPOJO() {
    }

    public WeekWeatherDataPOJO(float lat, float lon, String timezone, List<Daily> daily) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.daily = daily;
    }

    @Override
    public String toString() {
        return "WeekWeatherDataPOJO{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", daily=" + daily +
                '}';
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }


    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }
}
