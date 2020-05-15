package com.martyuk.weatherapp.ui.main.network;

import java.util.List;

public class Daily {
    private int dt;
    private int sunrise;
    private int sunset;
    private List<Weather> weather;
    private DailyTemperature temp;

    public Daily(int dt, int sunrise, int sunset, List<Weather> weather, DailyTemperature temp) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.weather = weather;
        this.temp = temp;
    }

    public Daily() {
    }

    @Override
    public String toString() {
        return "Daily{" +
                "dt=" + dt +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", weather=" + weather +
                ", temp=" + temp +
                '}';
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public DailyTemperature getTemp() {
        return temp;
    }

    public void setTemp(DailyTemperature temp) {
        this.temp = temp;
    }
}
