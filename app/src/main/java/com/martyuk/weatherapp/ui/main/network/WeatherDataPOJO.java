package com.martyuk.weatherapp.ui.main.network;

public class WeatherDataPOJO {
    CurrentWeatherPOJO current;

    @Override
    public String toString() {
        return "WeatherDataPOJO{" +
                "current=" + current +
                '}';
    }

    public CurrentWeatherPOJO getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeatherPOJO current) {
        this.current = current;
    }

    public WeatherDataPOJO() {
    }

    public WeatherDataPOJO(CurrentWeatherPOJO current) {
        this.current = current;
    }
}