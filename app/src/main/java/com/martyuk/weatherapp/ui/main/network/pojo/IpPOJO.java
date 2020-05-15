package com.martyuk.weatherapp.ui.main.network.pojo;

public class IpPOJO {
    float lat;
    float lon;
    String city;

    public IpPOJO() {
    }

    public IpPOJO(float lat, float lon, String city) {
        this.lat = lat;
        this.lon = lon;
        this.city = city;
    }

    @Override
    public String toString() {
        return "IpPOJO{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", city='" + city + '\'' +
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
