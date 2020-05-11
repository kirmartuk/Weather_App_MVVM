package com.martyuk.weatherapp.ui.main.week;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.martyuk.weatherapp.Passwords;
import com.martyuk.weatherapp.entities.WeekModel;
import com.martyuk.weatherapp.ui.main.network.WeatherRepository;
import com.martyuk.weatherapp.ui.main.network.pojo.WeekWeatherDataPOJO;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.martyuk.weatherapp.MainActivity.units;

public class WeekViewModel extends ViewModel {
    private MutableLiveData<WeekModel> dailyMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> progressBar = new MutableLiveData<>();

    public WeekViewModel() {
    }

    public LiveData<WeekModel> getDailyWeather() {
        loadWeekWeather();
        return dailyMutableLiveData;
    }

    public void setDailyWeather(MutableLiveData<WeekModel> dailyMutableLiveData) {
        this.dailyMutableLiveData = dailyMutableLiveData;
    }

    public void loadWeekWeather() {
        progressBar.postValue(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherRepository weatherRepository = retrofit.create(WeatherRepository.class);
        weatherRepository.loadWeekWeather(56.045279f, 92.969643f, units, Passwords.WEATHER_PASSWORD)
                .enqueue(new Callback<WeekWeatherDataPOJO>() {
                    @Override
                    public void onResponse(Call<WeekWeatherDataPOJO> call, Response<WeekWeatherDataPOJO> response) {
                        WeekModel weekModel = new WeekModel(response.body().getTimezone(), response.body().getDaily());
                        dailyMutableLiveData.setValue(weekModel);
                        progressBar.postValue(false);
                    }

                    @Override
                    public void onFailure(Call<WeekWeatherDataPOJO> call, Throwable t) {
                        Log.e("week", t.toString());
                        progressBar.postValue(false);


                    }
                });
    }
}
