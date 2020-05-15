package com.martyuk.weatherapp.ui.main.week;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.martyuk.weatherapp.Passwords;
import com.martyuk.weatherapp.entities.WeekModel;
import com.martyuk.weatherapp.ui.main.network.IpRepository;
import com.martyuk.weatherapp.ui.main.network.WeatherRepository;
import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;
import com.martyuk.weatherapp.ui.main.network.pojo.WeekWeatherDataPOJO;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.martyuk.weatherapp.MainActivity.units;
import static com.martyuk.weatherapp.Utils.getSystemLanguage;
import static com.martyuk.weatherapp.Utils.getRetrofit;

public class WeekViewModel extends ViewModel {
    private MutableLiveData<WeekModel> dailyMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> progress = new MutableLiveData<>();


    public WeekViewModel() {
    }

    public LiveData<WeekModel> getDailyWeather() {
        loadWeekWeather();
        return dailyMutableLiveData;
    }

    public LiveData<Boolean> getProgress() {
        return progress;
    }

    public void setDailyWeather(MutableLiveData<WeekModel> dailyMutableLiveData) {
        this.dailyMutableLiveData = dailyMutableLiveData;
    }

    public void loadWeekWeather() {
        progress.setValue(true);
        //get lat and lon from ip use system locale
        Log.e("lang", getSystemLanguage());
        IpRepository ipRepository =
                getRetrofit(IpRepository.BASE_URL).create(IpRepository.class);
        ipRepository.getIpData(getSystemLanguage()).enqueue(new Callback<IpPOJO>() {
            @Override
            public void onResponse(Call<IpPOJO> call, Response<IpPOJO> response) {
                IpPOJO latLonData = response.body();
                WeatherRepository weatherRepository =
                        getRetrofit(WeatherRepository.BASE_URL).create(WeatherRepository.class);
                weatherRepository
                        .loadWeekWeather(
                                latLonData.getLat(),
                                latLonData.getLon(),
                                units,
                                Passwords.WEATHER_PASSWORD,
                                getSystemLanguage())
                        .enqueue(new Callback<WeekWeatherDataPOJO>() {
                            @Override
                            public void onResponse(Call<WeekWeatherDataPOJO> call,
                                                   Response<WeekWeatherDataPOJO> response) {
                                WeekModel weekModel = new WeekModel(
                                        response.body().getTimezone(),
                                        response.body().getDaily());
                                dailyMutableLiveData.setValue(weekModel);
                            }

                            @Override
                            public void onFailure(Call<WeekWeatherDataPOJO> call, Throwable t) {
                                Log.e("weekViewModel", t.toString());
                                progress.setValue(false);

                            }
                        });
            }

            @Override
            public void onFailure(Call<IpPOJO> call, Throwable t) {
                progress.setValue(false);
                Log.e("weekViewModel", t.toString());
            }
        });


    }
}
