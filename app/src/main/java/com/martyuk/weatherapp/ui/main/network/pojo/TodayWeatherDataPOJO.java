package com.martyuk.weatherapp.ui.main.network.pojo;

import com.martyuk.weatherapp.ui.main.network.WeatherData;

import java.util.List;

public class TodayWeatherDataPOJO {
    private float lat;
    private float lon;
    private String timezone;
    private WeatherData current;
    private List<WeatherData> hourly;

    public TodayWeatherDataPOJO() {
    }

    public TodayWeatherDataPOJO(float lat, float lon, String timezone, WeatherData current, List<WeatherData> hourly) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.current = current;
        this.hourly = hourly;
    }

    @Override
    public String toString() {
        return "WeatherDataPOJO{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", current=" + current +
                ", hourly=" + hourly +
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

    public WeatherData getCurrent() {
        return current;
    }

    public void setCurrent(WeatherData current) {
        this.current = current;
    }

    public List<WeatherData> getHourly() {
        return hourly;
    }

    public void setHourly(List<WeatherData> hourly) {
        this.hourly = hourly;
    }
}
