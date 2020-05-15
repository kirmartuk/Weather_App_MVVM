package com.martyuk.weatherapp.ui.main.network;

import java.util.List;

public class WeatherData {
    private int dt;
    private float temp;
    private float feels_like;
    private List<Weather> weather;

    @Override
    public String toString() {
        return "WeatherData{" +
                "dt=" + dt +
                "temp=" + temp +
                ", feels_like=" + feels_like +
                ", weather=" + weather +
                '}';
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public WeatherData() {
    }

    public WeatherData(int dt, float temp, float feels_like, List<Weather> weather) {
        this.dt = dt;
        this.temp = temp;
        this.feels_like = feels_like;
        this.weather = weather;
    }
}
