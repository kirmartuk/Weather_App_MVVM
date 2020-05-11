package com.martyuk.weatherapp.ui.main.network;

import com.martyuk.weatherapp.ui.main.network.pojo.TodayWeatherDataPOJO;
import com.martyuk.weatherapp.ui.main.network.pojo.TomorrowWeatherDataPOJO;
import com.martyuk.weatherapp.ui.main.network.pojo.WeekWeatherDataPOJO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherRepository {
    String BASE_URL = "https://api.openweathermap.org/";

    @GET("data/2.5/onecall?exclude=daily")
    Call<TodayWeatherDataPOJO> loadTodayWeather(
            @Query("lat") float lat,
            @Query("lon") float lon,
            @Query("units") String units,
            @Query("appid") String key);

    @GET("data/2.5/onecall?exclude=current")
    Call<TomorrowWeatherDataPOJO> loadTomorrowWeather(
            @Query("lat") float lat,
            @Query("lon") float lon,
            @Query("units") String units,
            @Query("appid") String key);

    @GET("data/2.5/onecall?exclude=current,hourly")
    Call<WeekWeatherDataPOJO> loadWeekWeather(
            @Query("lat") float lat,
            @Query("lon") float lon,
            @Query("units") String units,
            @Query("appid") String key);
}
