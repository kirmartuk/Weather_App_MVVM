package com.martyuk.weatherapp.ui.main.network;

import java.util.ArrayList;

public class CurrentWeatherPOJO {
    private ArrayList<Object> weather_descriptions = new ArrayList<Object>();
    private String observation_time;
    private float wind_degree;
    private float visibility;
    private ArrayList<Object> weather_icons = new ArrayList<Object>();
    private float feelslike;
    private String is_day;
    private String wind_dir;
    private float pressure;
    private float cloudcover;
    private float precip;
    private float uv_index;
    private float temperature;
    private float humidity;
    private float wind_speed;
    private int weather_code;

    @Override
    public String toString() {
        return "CurrentWeatherPojo{" +
                "weather_descriptions=" + weather_descriptions +
                ", observation_time='" + observation_time + '\'' +
                ", wind_degree=" + wind_degree +
                ", visibility=" + visibility +
                ", weather_icons=" + weather_icons +
                ", feelslike=" + feelslike +
                ", is_day='" + is_day + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", pressure=" + pressure +
                ", cloudcover=" + cloudcover +
                ", precip=" + precip +
                ", uv_index=" + uv_index +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", wind_speed=" + wind_speed +
                ", weather_code=" + weather_code +
                '}';
    }

    public CurrentWeatherPOJO(ArrayList<Object> weather_descriptions, String observation_time, float wind_degree, float visibility, ArrayList<Object> weather_icons, float feelslike, String is_day, String wind_dir, float pressure, float cloudcover, float precip, float uv_index, float temperature, float humidity, float wind_speed, int weather_code) {
        this.weather_descriptions = weather_descriptions;
        this.observation_time = observation_time;
        this.wind_degree = wind_degree;
        this.visibility = visibility;
        this.weather_icons = weather_icons;
        this.feelslike = feelslike;
        this.is_day = is_day;
        this.wind_dir = wind_dir;
        this.pressure = pressure;
        this.cloudcover = cloudcover;
        this.precip = precip;
        this.uv_index = uv_index;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.weather_code = weather_code;
    }

    public CurrentWeatherPOJO() {
    }

    public ArrayList<Object> getWeather_descriptions() {
        return weather_descriptions;
    }

    public void setWeather_descriptions(ArrayList<Object> weather_descriptions) {
        this.weather_descriptions = weather_descriptions;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(String observation_time) {
        this.observation_time = observation_time;
    }

    public float getWind_degree() {
        return wind_degree;
    }

    public void setWind_degree(float wind_degree) {
        this.wind_degree = wind_degree;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public ArrayList<Object> getWeather_icons() {
        return weather_icons;
    }

    public void setWeather_icons(ArrayList<Object> weather_icons) {
        this.weather_icons = weather_icons;
    }

    public float getFeelslike() {
        return feelslike;
    }

    public void setFeelslike(float feelslike) {
        this.feelslike = feelslike;
    }

    public String getIs_day() {
        return is_day;
    }

    public void setIs_day(String is_day) {
        this.is_day = is_day;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(float cloudcover) {
        this.cloudcover = cloudcover;
    }

    public float getPrecip() {
        return precip;
    }

    public void setPrecip(float precip) {
        this.precip = precip;
    }

    public float getUv_index() {
        return uv_index;
    }

    public void setUv_index(float uv_index) {
        this.uv_index = uv_index;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }
}