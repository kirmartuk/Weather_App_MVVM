package com.martyuk.weatherapp.ui.main.network.pojo;

import com.martyuk.weatherapp.ui.main.network.Daily;
import com.martyuk.weatherapp.ui.main.network.WeatherData;

import java.util.List;

public class TomorrowWeatherDataPOJO {
    private float lat;
    private float lon;
    private String timezone;
    private List<Daily> daily;
    private List<WeatherData> hourly;

    @Override
    public String toString() {
        return "TomorrowWeatherDataPOJO{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", daily=" + daily +
                ", hourly=" + hourly +
                '}';
    }

    public TomorrowWeatherDataPOJO() {
    }

    public TomorrowWeatherDataPOJO(float lat, float lon, String timezone, List<Daily> daily, List<WeatherData> hourly) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.daily = daily;
        this.hourly = hourly;
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

    public List<WeatherData> getHourly() {
        return hourly;
    }

    public void setHourly(List<WeatherData> hourly) {
        this.hourly = hourly;
    }
}
