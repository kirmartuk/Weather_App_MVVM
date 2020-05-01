package com.martyuk.weatherapp.ui.main.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRepository {
    String BASE_URL = "http://api.weatherstack.com/";
    @GET("current")
    Call<WeatherDataPOJO> loadCurrentWeather(@Query("query") String city, @Query("access_key") String key);
}
